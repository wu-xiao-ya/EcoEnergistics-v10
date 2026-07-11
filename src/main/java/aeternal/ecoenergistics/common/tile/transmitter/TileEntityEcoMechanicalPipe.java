package aeternal.ecoenergistics.common.tile.transmitter;

import aeternal.ecoenergistics.common.block.states.BlockStateEcoTransmitter.EcoTransmitterType;
import aeternal.ecoenergistics.common.tier.EcoPipeTier;
import aeternal.ecoenergistics.common.tier.MEEAlloyTier;
import aeternal.ecoenergistics.common.tier.MEETiers;
import io.netty.buffer.ByteBuf;
import mekanism.api.Action;
import mekanism.api.TileNetworkList;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.fluid.IMekanismFluidHandler;
import mekanism.api.transmitters.TransmissionType;
import mekanism.common.capabilities.fluid.DynamicFluidHandler;
import mekanism.common.tile.transmitter.TileEntitySidedPipe.ConnectionType;
import mekanism.common.transmitters.grid.FluidNetwork;
import mekanism.common.util.CapabilityUtils;
import mekanism.common.util.PipeUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TileEntityEcoMechanicalPipe extends TileEntityEcoTransmitter<IFluidHandler, FluidNetwork, FluidStack> implements IMekanismFluidHandler {

    public EcoPipeTier tier = EcoPipeTier.ADVANCED;

    public float currentScale;

    public FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME);

    public FluidStack lastWrite;

    private final IExtendedFluidTank fluidTank = new EcoFluidTank();
    private final DynamicFluidHandler fluidHandler = new DynamicFluidHandler(
            this::getFluidTanksForSide,
            this::canInsertFluidSide,
            this::canExtractFluidSide,
            this::onContentsChanged
    );

    private int nextTransfer = 0;

    public void onContentsChanged() {
        markChunkDirty();
    }

    @Override
    public MEETiers getBaseTier() {
        return tier.getBaseTier();
    }

    @Override
    public void setBaseTier(MEETiers baseTier) {
        tier = EcoPipeTier.get(baseTier);
        buffer.setCapacity(getCapacity());
    }

    @Override
    public void doRestrictedTick() {
        if (!getWorld().isRemote) {
            updateShare();
            if (nextTransfer <= 0) {
                IFluidHandler[] connectedAcceptors = PipeUtils.getConnectedAcceptors(getPos(), getWorld());
                boolean successAtLeaseOnce = false;
                for (EnumFacing side : getConnections(ConnectionType.PULL)) {
                    IFluidHandler container = connectedAcceptors[side.ordinal()];
                    if (container != null) {
                        FluidStack received = container.drain(getAvailablePull(), false);
                        if (received != null && received.amount != 0 && takeFluid(received, false) == received.amount) {
                            container.drain(takeFluid(received, true), true);
                            successAtLeaseOnce = true;
                        }
                    }
                }
                if (!successAtLeaseOnce) {
                    nextTransfer = 20;
                }
            } else {
                nextTransfer--;
            }
        }
        super.doRestrictedTick();
    }

    @Override
    public void updateShare() {
        if (getTransmitter().hasTransmitterNetwork() && getTransmitter().getTransmitterNetworkSize() > 0) {
            FluidStack last = getSaveShare();
            if ((last != null && !(lastWrite != null && lastWrite.amount == last.amount && lastWrite.getFluid() == last.getFluid())) || (last == null && lastWrite != null)) {
                lastWrite = last;
                markChunkDirty();
            }
        }
    }

    private FluidStack getSaveShare() {
        FluidNetwork transmitterNetwork = getTransmitter().getTransmitterNetwork();
        if (getTransmitter().hasTransmitterNetwork() && transmitterNetwork.buffer != null) {
            int remain = transmitterNetwork.buffer.amount % transmitterNetwork.transmittersSize();
            int toSave = transmitterNetwork.buffer.amount / transmitterNetwork.transmittersSize();
            if (transmitterNetwork.firstTransmitter().equals(getTransmitter())) {
                toSave += remain;
            }
            return PipeUtils.copy(transmitterNetwork.buffer, toSave);
        }
        return null;
    }

    @Override
    public void onChunkUnload() {
        if (!getWorld().isRemote && getTransmitter().hasTransmitterNetwork()) {
            if (lastWrite != null && getTransmitter().getTransmitterNetwork().buffer != null) {
                getTransmitter().getTransmitterNetwork().buffer.amount -= lastWrite.amount;
                if (getTransmitter().getTransmitterNetwork().buffer.amount <= 0) {
                    getTransmitter().getTransmitterNetwork().buffer = null;
                }
            }
        }
        super.onChunkUnload();
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbtTags) {
        super.readCustomNBT(nbtTags);
        if (nbtTags.hasKey("tier")) {
            tier = EcoPipeTier.values()[nbtTags.getInteger("tier")];
        }
        buffer.setCapacity(getCapacity());
        if (nbtTags.hasKey("cacheFluid")) {
            buffer.setFluid(FluidStack.loadFluidStackFromNBT(nbtTags.getCompoundTag("cacheFluid")));
        } else {
            buffer.setFluid(null);
        }
    }


    @Override
    public void writeCustomNBT(NBTTagCompound nbtTags) {
        super.writeCustomNBT(nbtTags);
        if (lastWrite != null && lastWrite.amount > 0) {
            nbtTags.setTag("cacheFluid", lastWrite.writeToNBT(new NBTTagCompound()));
        } else {
            nbtTags.removeTag("cacheFluid");
        }
        nbtTags.setInteger("tier", tier.ordinal());
    }

    @Override
    public TransmissionType getTransmissionType() {
        return TransmissionType.FLUID;
    }

    @Override
    public EcoTransmitterType getTransmitterType() {
        return EcoTransmitterType.MECHANICAL_PIPE;
    }

    @Override
    public boolean isValidAcceptor(TileEntity acceptor, EnumFacing side) {
        return PipeUtils.isValidAcceptorOnSide(acceptor, side);
    }

    @Override
    public boolean isValidTransmitter(TileEntity tileEntity) {
        if (!super.isValidTransmitter(tileEntity)) {
            return false;
        }
        if (!(tileEntity instanceof TileEntityEcoMechanicalPipe)) {
            return true;
        }
        FluidStack buffer = getBufferWithFallback();
        FluidStack otherBuffer = ((TileEntityEcoMechanicalPipe) tileEntity).getBufferWithFallback();
        return buffer == null || otherBuffer == null || buffer.isFluidEqual(otherBuffer);
    }

    @Override
    public FluidNetwork createNewNetwork() {
        return new FluidNetwork();
    }

    @Override
    public FluidNetwork createNetworkByMerging(Collection<FluidNetwork> networks) {
        return new FluidNetwork(networks);
    }

    @Override
    protected boolean canHaveIncompatibleNetworks() {
        return true;
    }

    @Override
    public int getCapacity() {
        return tier.getPipeCapacity();
    }

    @Nullable
    @Override
    public FluidStack getBuffer() {
        return buffer == null ? null : buffer.getFluid();
    }

    @Override
    public void takeShare() {
        if (getTransmitter().hasTransmitterNetwork() && getTransmitter().getTransmitterNetwork().buffer != null && lastWrite != null) {
            getTransmitter().getTransmitterNetwork().buffer.amount -= lastWrite.amount;
            buffer.setFluid(lastWrite);
        }
    }

    private List<IExtendedFluidTank> getFluidTanksForSide(EnumFacing side) {
        if (isRedstoneActivated() || (side != null && !canConnect(side))) {
            return Collections.emptyList();
        }
        return Collections.singletonList(fluidTank);
    }

    private boolean canInsertFluidSide(EnumFacing side) {
        return side == null || getConnectionType(side) == ConnectionType.NORMAL || getConnectionType(side) == ConnectionType.PULL;
    }

    private boolean canExtractFluidSide(EnumFacing side) {
        return side == null || getConnectionType(side) == ConnectionType.NORMAL || getConnectionType(side) == ConnectionType.PUSH;
    }

    @Override
    public int fill(EnumFacing from, @Nonnull FluidStack resource, boolean doFill) {
        return getFluidCapability(from).fill(resource, doFill);
    }

    @Override
    public boolean canFill(EnumFacing from, @Nonnull FluidStack fluid) {
        return fluid != null
                && fluid.getFluid() != null
                && (from == null || fluidHandler.canInsertFluid(from));
    }

    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        if (from != null && getConnectionType(from) != ConnectionType.NONE) {
            //Our buffer or the network's buffer if we have a network
            return getAllTanks();
        }
        return PipeUtils.EMPTY;
    }

    public FluidTankInfo[] getAllTanks() {
        if (getTransmitter().hasTransmitterNetwork()) {
            FluidNetwork network = getTransmitter().getTransmitterNetwork();
            return new FluidTankInfo[]{new FluidTankInfo(network.getBuffer(), network.getCapacity())};
        }
        return new FluidTankInfo[]{buffer.getInfo()};
    }

    @Override
    public boolean canHandleFluid() {
        return true;
    }

    @Override
    public List<IExtendedFluidTank> getFluidTanks(EnumFacing side) {
        return fluidHandler.getFluidTanks(side);
    }

    @Override
    public boolean canInsertFluid(EnumFacing side) {
        return fluidHandler.canInsertFluid(side);
    }

    @Override
    public boolean canExtractFluid(EnumFacing side) {
        return fluidHandler.canExtractFluid(side);
    }

    @Override
    public FluidStack insertFluid(int tank, FluidStack stack, EnumFacing side, Action action) {
        return fluidHandler.insertFluid(tank, stack, side, action);
    }

    @Override
    public FluidStack extractFluid(int tank, int amount, EnumFacing side, Action action) {
        return fluidHandler.extractFluid(tank, amount, side, action);
    }

    @Override
    public FluidStack insertFluid(FluidStack stack, EnumFacing side, Action action) {
        return fluidHandler.insertFluid(stack, side, action);
    }

    @Override
    public FluidStack extractFluid(int amount, EnumFacing side, Action action) {
        return fluidHandler.extractFluid(amount, side, action);
    }

    @Override
    public FluidStack extractFluid(FluidStack stack, EnumFacing side, Action action) {
        return fluidHandler.extractFluid(stack, side, action);
    }

    public int getPullAmount() {
        return tier.getPipePullAmount();
    }

    @Override
    public IFluidHandler getCachedAcceptor(EnumFacing side) {
        TileEntity tile = getCachedTile(side);
        if (CapabilityUtils.hasCapability(tile, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite())) {
            return CapabilityUtils.getCapability(tile, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
        }
        return null;
    }

    public int getAvailablePull() {
        if (getTransmitter().hasTransmitterNetwork()) {
            return Math.min(getPullAmount(), getTransmitter().getTransmitterNetwork().getFluidNeeded());
        }
        return Math.min(getPullAmount(), buffer.getCapacity() - buffer.getFluidAmount());
    }

    public int takeFluid(FluidStack fluid, boolean doEmit) {
        if (getTransmitter().hasTransmitterNetwork()) {
            return getTransmitter().getTransmitterNetwork().emit(fluid, doEmit);
        }
        return buffer.fill(fluid, doEmit);
    }

    @Override
    public boolean upgrade(MEEAlloyTier tierOrdinal) {
        if (tier.ordinal() < MEETiers.NEUTRON.ordinal() && tierOrdinal.ordinal() == tier.ordinal()) {
            tier = EcoPipeTier.values()[tier.ordinal() + 1];
            markDirtyTransmitters();
            sendDesc = true;
            return true;
        }
        return false;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) throws Exception {
        tier = EcoPipeTier.values()[dataStream.readInt()];
        super.handlePacketData(dataStream);
    }

    @Override
    public TileNetworkList getNetworkedData(TileNetworkList data) {
        data.add(tier.ordinal());
        super.getNetworkedData(data);
        return data;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing side) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                && (side == null || fluidHandler.canInsertFluid(side) || fluidHandler.canExtractFluid(side))
                || super.hasCapability(capability, side);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidCapability(side));
        }
        return super.getCapability(capability, side);
    }

    private IFluidHandler getFluidCapability(@Nullable EnumFacing side) {
        return side == null ? fluidHandler : new SideAwareFluidHandler(side);
    }

    private class SideAwareFluidHandler implements IFluidHandler {

        private final EnumFacing side;

        private SideAwareFluidHandler(EnumFacing side) {
            this.side = side;
        }

        @Nonnull
        @Override
        public IFluidTankProperties[] getTankProperties() {
            List<IExtendedFluidTank> tanks = fluidHandler.getFluidTanks(side);
            IFluidTankProperties[] properties = new IFluidTankProperties[tanks.size()];
            for (int i = 0; i < tanks.size(); i++) {
                IExtendedFluidTank tank = tanks.get(i);
                properties[i] = new IFluidTankProperties() {
                    @Nullable
                    @Override
                    public FluidStack getContents() {
                        return tank.getFluid();
                    }

                    @Override
                    public int getCapacity() {
                        return tank.getCapacity();
                    }

                    @Override
                    public boolean canFill() {
                        return fluidHandler.canInsertFluid(side);
                    }

                    @Override
                    public boolean canDrain() {
                        return fluidHandler.canExtractFluid(side);
                    }

                    @Override
                    public boolean canFillFluidType(FluidStack fluidStack) {
                        return fluidStack != null
                                && fluidStack.getFluid() != null
                                && canFill()
                                && tank.isFluidValid(fluidStack);
                    }

                    @Override
                    public boolean canDrainFluidType(FluidStack fluidStack) {
                        FluidStack current = tank.getFluid();
                        return fluidStack != null
                                && fluidStack.getFluid() != null
                                && canDrain()
                                && current != null
                                && current.isFluidEqual(fluidStack);
                    }
                };
            }
            return properties;
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (resource == null || resource.amount <= 0) {
                return 0;
            }
            FluidStack remainder = fluidHandler.insertFluid(resource, side, Action.get(doFill));
            return remainder == null ? resource.amount : resource.amount - remainder.amount;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource == null || resource.amount <= 0) {
                return null;
            }
            FluidStack drained = fluidHandler.extractFluid(resource, side, Action.get(doDrain));
            return drained == null || drained.amount <= 0 ? null : drained;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (maxDrain <= 0) {
                return null;
            }
            FluidStack drained = fluidHandler.extractFluid(maxDrain, side, Action.get(doDrain));
            return drained == null || drained.amount <= 0 ? null : drained;
        }
    }

    private class EcoFluidTank implements IExtendedFluidTank {

        @Override
        public FluidStack getFluid() {
            FluidStack active = getTransmitter().hasTransmitterNetwork()
                    ? getTransmitter().getTransmitterNetwork().getBuffer()
                    : buffer.getFluid();
            return active == null || active.amount <= 0 ? null : active;
        }

        @Override
        public int getFluidAmount() {
            FluidStack active = getFluid();
            return active == null ? 0 : active.amount;
        }

        @Override
        public int getCapacity() {
            return getTransmitter().hasTransmitterNetwork()
                    ? getTransmitter().getTransmitterNetwork().getCapacity()
                    : TileEntityEcoMechanicalPipe.this.getCapacity();
        }

        @Override
        public void setStack(FluidStack stack) {
            if (stack != null && !isFluidValid(stack)) {
                return;
            }
            setStackUnchecked(stack);
        }

        @Override
        public void setStackUnchecked(FluidStack stack) {
            FluidStack active = stack == null || stack.amount <= 0
                    ? null
                    : new FluidStack(stack, Math.min(stack.amount, getCapacity()));
            if (getTransmitter().hasTransmitterNetwork()) {
                getTransmitter().getTransmitterNetwork().setBuffer(active);
            } else {
                buffer.setFluid(active);
            }
            onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack != null && stack.getFluid() != null;
        }

        @Override
        public void onContentsChanged() {
            markChunkDirty();
        }
    }
}

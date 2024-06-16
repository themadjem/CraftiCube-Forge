package com.themadjem.crafticube.block.entity;

import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.custom.CrafticubeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CrafticubeBlockEntity extends BlockEntity implements Nameable {
    public final int FLUID_LIMIT;
    private final ItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> lazyItemHandler;
    private LazyOptional<IFluidHandler> lazyFluidHandler;

    private final CrafticubeFluidTank[] fluidTanks;

    public CrafticubeBlockEntity(BlockEntityType<? extends CrafticubeBlockEntity> blockEntityType, BlockPos pPos, BlockState pBlockState, int invSlots, int tankSize) {
        super(blockEntityType, pPos, pBlockState);
        itemHandler = new ItemStackHandler(invSlots);
        lazyItemHandler = LazyOptional.of(() -> this.itemHandler);
        FLUID_LIMIT = tankSize;
        fluidTanks = new CrafticubeFluidTank[]{
                new CrafticubeFluidTank(FLUID_LIMIT, this),
                new CrafticubeFluidTank(FLUID_LIMIT, this),
                new CrafticubeFluidTank(FLUID_LIMIT, this),
                new CrafticubeFluidTank(FLUID_LIMIT, this)
        };
        lazyFluidHandler = LazyOptional.of(CrafticubeFluidHandler::new);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove) {
            if (cap == ForgeCapabilities.ITEM_HANDLER) {
                return lazyItemHandler.cast();
            }
            if (cap == ForgeCapabilities.FLUID_HANDLER) {
                return lazyFluidHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(CrafticubeFluidHandler::new);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        CraftiCube.logDebug("Loading CrafticubeBlockEntity");
        if (tag.contains("inv")) {
            CraftiCube.logDebug(tag.getCompound("inv").toString());
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }
        for (int i = 0; i < fluidTanks.length; i++) {
            String tankNo = "tank" + i;
            if (tag.contains(tankNo)) {
                fluidTanks[i].readFromNBT(tag.getCompound(tankNo));

                CraftiCube.logDebug(tag.getCompound(tankNo).toString());
            }
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        CraftiCube.logDebug("Saving CrafticubeBlockEntity...");
//        CraftiCube.logStackTrace();
        CompoundTag items = itemHandler.serializeNBT();
//        CraftiCube.logDebug(items.toString());
        tag.put("inv", items);
        for (int i = 0; i < fluidTanks.length; i++) {
            CompoundTag fluidTag = fluidTanks[i].writeToNBT(new CompoundTag());
//            CraftiCube.logDebug("Tank " + i + ": " + fluidTag.toString());
            tag.put("tank" + i, fluidTag);
        }
    }

    /*
     * This does not drop fluids.
     *
     * I also am not sure if I want it to drop its contents or keep it,
     * it is expensive to craft, so having it be able to
     * keep its inventory on pickup is feasable
     * */
    public void dropContents() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void printContents(Level pLevel, Player pPlayer) {
        if (!pLevel.isClientSide) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    pPlayer.sendSystemMessage(Component.literal("Slot " + i + ": " + stack.getCount() + "x " + stack.getHoverName().getString()));
                }
            }
            for (int i = 0; i < fluidTanks.length; i++) {
                if (!fluidTanks[i].isEmpty()) {
                    pPlayer.sendSystemMessage(Component.literal("Tank " + i + ": " + fluidTanks[i].getFluidAmount() + "mB " + fluidTanks[i].getFluid().getDisplayName().getString()));
                }
            }
        }
    }

    @Override
    public @NotNull Component getName() {
        return Component.literal("Generic Crafticube");
    }

    public boolean isEmpty() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack item = itemHandler.getStackInSlot(i);
            if (!item.isEmpty()) {
                CraftiCube.logDebug("Item Present, not empty");
                return false;
            }
        }
        for (FluidTank tank : fluidTanks) {
            if (!tank.isEmpty()) {
                CraftiCube.logDebug("Fluid Present, not empty");
                return false;
            }
        }
        return true;
    }

    private class CrafticubeFluidHandler implements IFluidHandler {
        @Override
        public int getTanks() {
            return fluidTanks.length;
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int tank) {
            return fluidTanks[tank].getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return fluidTanks[tank].getCapacity();
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            boolean fluidValid = fluidTanks[tank].isFluidValid(stack);
            CraftiCube.logDebug("isFluidValid for " + stack.getDisplayName() + " in tank " + tank + ": " + fluidValid);
            return fluidValid;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            // List to keep track of potential fill results
            ArrayList<TankFillResult> results = new ArrayList<>();

            // Iterate over each tank and simulate the fill process
            for (int i = 0; i < fluidTanks.length; i++) {
                CrafticubeFluidTank tank = fluidTanks[i];

                if (tank.isEmpty() || tank.getFluid().isFluidEqual(resource)) {
                    results.add(new TankFillResult(i, tank.fill(resource, FluidAction.SIMULATE)));
                } else {
                    results.add(new TankFillResult(i, 0));
                }
            }

            // Process the simulation results
            for (TankFillResult result : results) {
                if (result.result > 0) {
                    // If not simulating, perform the actual fill
                    if (action.execute()) {
                        return fluidTanks[result.index].fill(resource, action);
                    }
                    return result.result;
                }
            }

            return 0;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            for (FluidTank tank : fluidTanks) {
                if (tank.getFluid().isFluidEqual(resource)) {
                    return tank.drain(resource, action);
                }
            }
            return FluidStack.EMPTY;
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            // Add simulation check

            for (FluidTank tank : fluidTanks) {
                FluidStack drained = tank.drain(maxDrain, action);
                if (!drained.isEmpty()) {
                    return drained;
                }
            }
            return FluidStack.EMPTY;
        }

        private static class TankFillResult {
            int index;
            int result;

            TankFillResult(int idx, int res) {
                this.index = idx;
                this.result = res;
            }
        }
    }


    private static class CrafticubeFluidTank extends FluidTank {
        private final CrafticubeBlockEntity blockEntity;

        public CrafticubeFluidTank(int capacity, CrafticubeBlockEntity blockEntity) {
            super(capacity);
            this.blockEntity = blockEntity;
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            if (blockEntity != null) {
                blockEntity.setChanged(); // Mark the block entity as needing to be saved
            }
        }
    }

}

package com.mrcrayfish.device.programs.gitweb.module;

import com.mrcrayfish.device.programs.gitweb.component.container.ContainerBox;
import com.mrcrayfish.device.programs.gitweb.component.container.FurnaceBox;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class FurnaceModule extends ContainerModule
{
    @Override
    public int getHeight()
    {
        return FurnaceBox.HEIGHT;
    }

    @Override
    public ContainerBox createContainer(Map<String, String> data)
    {
        ItemStack input = getItem(data, "slot-input");
        ItemStack fuel = getItem(data, "slot-fuel");
        ItemStack result = getItem(data, "slot-result");
        return new FurnaceBox(input, fuel, result);
    }
}

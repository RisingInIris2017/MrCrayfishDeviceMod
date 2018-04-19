package com.mrcrayfish.device.programs.gitweb.module;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.core.Laptop;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class HeaderModule extends Module
{
    @Override
    public String[] getRequiredData()
    {
        return new String[] { "text" };
    }

    @Override
    public int calculateHeight(Map<String, String> data, int width)
    {
        if(data.containsKey("scale"))
        {
            return Integer.parseInt(data.get("scale")) * Laptop.fontRenderer.FONT_HEIGHT + 10;
        }
        return Laptop.fontRenderer.FONT_HEIGHT + 10;
    }

    @Override
    public void generate(Application app, Layout layout, int width, Map<String, String> data)
    {
        Label label = new Label(data.get("text"), width / 2, 5);
        label.setAlignment(Component.ALIGN_CENTER);

        int scale = 1;
        if(data.containsKey("scale"))
        {
            scale = Integer.parseInt(data.get("scale"));
        }
        label.setScale(scale);

        String align = data.getOrDefault("align", "center");
        if("left".equals(align))
        {
            label.left = 5;
            label.setAlignment(Component.ALIGN_LEFT);
        }
        else if("right".equals(align))
        {
            label.left = width - 5;
            label.setAlignment(Component.ALIGN_RIGHT);
        }

        layout.addComponent(label);
    }
}
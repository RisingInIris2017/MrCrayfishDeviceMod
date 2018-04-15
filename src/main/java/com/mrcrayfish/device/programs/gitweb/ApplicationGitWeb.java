package com.mrcrayfish.device.programs.gitweb;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Dialog.Confirmation;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.*;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.TextField;
import com.mrcrayfish.device.api.app.listener.KeyListener;
import com.mrcrayfish.device.api.task.Callback;
import com.mrcrayfish.device.api.utils.OnlineRequest;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.programs.gitweb.component.GitWebFrame;
import com.mrcrayfish.device.programs.gitweb.layout.TextLayout;
import com.mrcrayfish.device.programs.system.layout.StandardLayout;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.regex.Matcher;

/**
 * The Device Mod implementations of an internet browser. Originally created by MinecraftDoodler.
 * Licensed under GPL 3.0
 */
public class ApplicationGitWeb extends Application
{
    private Layout layoutBrowser;
    private Layout layoutPref;

    private Button btnSearch;
    private Button btnHome;
    private Button btnSettings;

    private GitWebFrame webFrame;
    private TextField textFieldAddress;
    private Spinner spinnerLoading;
    private TextLayout scrollable;

    @Override
    public void init()
    {
        layoutBrowser = new StandardLayout("GitWeb", 362, 240, this, null);
        layoutBrowser.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
        {
            Color color = new Color(Laptop.getSystem().getSettings().getColorScheme().getItemBackgroundColor());
            Gui.drawRect(x, y + 21, x + width, y + 164, Color.GRAY.getRGB());
        });

        layoutPref = new Layout(200, 120);

        textFieldAddress = new TextField(2, 2, 304);
        textFieldAddress.setPlaceholder("Enter Address");
        textFieldAddress.setKeyListener(c ->
        {
            System.out.println(c);
            return true;
        });
        layoutBrowser.addComponent(textFieldAddress);

        spinnerLoading = new Spinner(291, 4);
        spinnerLoading.setVisible(false);
        layoutBrowser.addComponent(spinnerLoading);

        btnSearch = new Button(308, 2, 16, 16, Icons.ARROW_RIGHT);
        btnSearch.setToolTip("Refresh", "Loads the entered address.");
        btnSearch.setClickListener((mouseX, mouseY, mouseButton) -> webFrame.loadWebsite(this.getAddress()));
        layoutBrowser.addComponent(btnSearch);

        btnHome = new Button(326, 2, 16, 16, Icons.HOME);
        btnHome.setToolTip("Home", "Loads page set in settings.");
        btnHome.setClickListener((mouseX, mouseY, mouseButton) -> webFrame.loadWebsite("welcome.official"));
        layoutBrowser.addComponent(btnHome);

        btnSettings = new Button(344, 2, 16, 16, Icons.WRENCH);
        btnSettings.setToolTip("Settings", "Change your preferences.");
        btnSettings.setClickListener((mouseX, mouseY, mouseButton) -> this.setCurrentLayout(layoutPref));
        layoutBrowser.addComponent(btnSettings);

        webFrame = new GitWebFrame(5, 25, 355, 135);
        webFrame.loadWebsite("welcome.official");
        webFrame.setLoadingCallback((s, success) ->
        {
            if(success)
            {
                spinnerLoading.setVisible(true);
                textFieldAddress.setFocused(false);
                textFieldAddress.setEditable(false);
                textFieldAddress.setText(s);
                btnSearch.setEnabled(false);
            }
        });
        webFrame.setLoadedCallback((s, success) ->
        {
            if(success)
            {
                spinnerLoading.setVisible(false);
                textFieldAddress.setEditable(true);
                btnSearch.setEnabled(true);
            }
        });
        layoutBrowser.addComponent(webFrame);

        //this.loadLink("welcome.official", false);
        this.setCurrentLayout(layoutBrowser);
    }

    @Override
    public void handleKeyTyped(char character, int code)
    {
        super.handleKeyTyped(character, code);
    }

    private String getAddress()
    {
        return textFieldAddress.getText().replace("\\s+", "");
    }

    @Override
    public void load(NBTTagCompound tag) {}

    @Override
    public void save(NBTTagCompound tag) {}
}
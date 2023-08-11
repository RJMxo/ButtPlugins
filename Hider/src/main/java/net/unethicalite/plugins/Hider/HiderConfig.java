package net.unethicalite.plugins.Hider;

import net.runelite.client.config.*;
import net.runelite.client.config.ConfigTitle;


@ConfigGroup("Hider")
public interface HiderConfig extends Config
{

    @ConfigTitle(
            keyName = "delayConfig",
            name = "Sleep Delay Configuration",
            description = "Configure how the bot handles sleep delays",
            position = 2
    )
    boolean delayConfig = false;

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMin",
            name = "Sleep Min",
            description = "",
            position = 3,
            section = "delayConfig"
    )
    default int sleepMin()
    {
        return 60;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMaximus",
            name = "Sleep Maximus",
            description = "",
            position = 4,
            section = "delayConfig"
    )
    default int sleepMax()
    {
        return 350;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepTarget",
            name = "Sleep Target",
            description = "",
            position = 5,
            section = "delayConfig"
    )
    default int sleepTarget()
    {
        return 100;
    }

    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepDeviation",
            name = "Sleep Deviation",
            description = "",
            position = 6,
            section = "delayConfig"
    )
    default int sleepDeviation()
    {
        return 10;
    }

    @ConfigItem(
            keyName = "sleepWeightedDistribution",
            name = "Sleep Weighted Distribution",
            description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
            position = 7,
            section = "delayConfig"
    )
    default boolean sleepWeightedDistribution()
    {
        return false;
    }

    @ConfigTitle(
            keyName = "delayTickConfig",
            name = "Game Tick Configuration",
            description = "Configure how the bot handles game tick delays, 1 game tick equates to roughly 600ms",
            position = 8
    )
    String delayTickConfig = "delayTickConfig";

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayMin",
            name = "Game Tick Min",
            description = "",
            position = 9,
            section = "delayTickConfig"
    )
    default int tickDelayMin()
    {
        return 1;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayMax",
            name = "Game Tick Max",
            description = "",
            position = 10,
            section = "delayTickConfig"
    )
    default int tickDelayMax()
    {
        return 3;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayTarget",
            name = "Game Tick Target",
            description = "",
            position = 11,
            section = "delayTickConfig"
    )
    default int tickDelayTarget()
    {
        return 2;
    }

    @Range(
            min = 0,
            max = 10
    )
    @ConfigItem(
            keyName = "tickDelayDeviation",
            name = "Game Tick Deviation",
            description = "",
            position = 12,
            section = "delayTickConfig"
    )
    default int tickDelayDeviation()
    {
        return 1;
    }

    @ConfigItem(
            keyName = "tickDelayWeightedDistribution",
            name = "Game Tick Weighted Distribution",
            description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
            position = 13,
            section = "delayTickConfig"
    )
    default boolean tickDelayWeightedDistribution()
    {
        return false;
    }

    @ConfigTitle(
            keyName = "instructionsTitle",
            name = "instruction",
            description = "",
            position = 16
    )
    String instruction = "Setup";

    @ConfigItem(
            keyName = "instructions",
            name = "",
            description = "Instructions. Don't enter anything into this field",
            position = 20,
            title = "instructionsTitle"
    )
    default String instructions()
    {
        return "This shit good for hiding. Like in ya mums basement. Lochi fucks kids.";
    }

    @ConfigItem(
            keyName = "selectHide",
            name = "Hide",
            description = "Select which hide you're using.",
            position = 22
    )
    default hideType craftHide()	{ return hideType.Sapphire; }



    @ConfigItem(
            keyName = "startButton",
            name = "Start/Stop",
            description = "Test button that changes variable value",
            position = 100
    )
    default Button startButton()
    {
        return new Button();
    }



}
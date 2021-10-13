package PrismaMod;

import PrismaMod.cards.ChloeCards.Exhaust.ChloeExhaustOne;
import PrismaMod.cards.ChloeCards.Exhaust.ChloeExhaustThree;
import PrismaMod.cards.ChloeCards.Exhaust.ChloeExhaustTwo;
import PrismaMod.cards.ChloeCards.Misc.*;
import PrismaMod.cards.ChloeCards.Normal.*;
import PrismaMod.cards.ChloeCards.RelicBuff.ChloeBetterTracePools;
import PrismaMod.cards.ChloeCards.RelicBuff.ChloeDoubleRelicSpeed;
import PrismaMod.cards.ChloeCards.RelicBuff.ChloeGreatTracePools;
import PrismaMod.cards.ChloeCards.Special.*;
import PrismaMod.cards.ChloeCards.TraceCards.*;
import PrismaMod.cards.IllyaCards.Misc.*;
import PrismaMod.cards.IllyaCards.Normal.*;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.*;
import PrismaMod.cards.IllyaCards.Orbs.*;
import PrismaMod.cards.IllyaCards.Poison.*;
import PrismaMod.cards.MiyuCards.Misc.MiyuBride;
import PrismaMod.cards.MiyuCards.Misc.MiyuChristmas;
import PrismaMod.cards.MiyuCards.Misc.MiyuDevil;
import PrismaMod.cards.MiyuCards.Misc.MiyuMaid;
import PrismaMod.cards.MiyuCards.Normal.*;
import PrismaMod.cards.MiyuCards.Orbs.*;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Illya;
import PrismaMod.characters.Miyu;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.relics.*;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PrismaMod.util.IDCheckDontTouchPls;
import PrismaMod.util.TextureLoader;
import PrismaMod.variables.DefaultCustomVariable;
import PrismaMod.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

@SpireInitializer
public class DefaultMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.

    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
    private static String modID;

    public static final TextureAtlas.AtlasRegion originalFrame = ImageMaster.CARD_FRAME_POWER_RARE;
    public static final TextureAtlas.AtlasRegion originalFrame_L = ImageMaster.CARD_FRAME_POWER_RARE_L;

    //Idle Animations
    public static String blank = "PrismaModResources/images/char/BlankAnim/BlankSpriter.scml";
    /*//Attack Animations
    public static String illyaOrbAttackAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaOrbAttackSprite.scml";
    public static String illyaBlastAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaBlastSprite.scml";
    public static String illyaSwipeAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaSwipeSprite.scml";
    //Defend Animation (Gain block)
    public static String illyaBlockAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaBlockSprite.scml";
    //Take Damage Animation
    public static String illyaAttackedAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaAttackedSprite.scml";
    public static String illyaBigAttackedAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaBigAttackedSprite.scml";
    //Channel Orb animation
    public static String illyaChannelAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaChannelSprite.scml";
    //Install Animation
    public static String illyaInstallAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/IllyaInstallSprite.scml";
    //public static AbstractAnimation charAnimationLocation = illyaIdleAnimation;    //Default to the idle animation

    //FAST ANIMATIONS
    //Idle Animations
    public static String FAST_illyaIdleAnimation = illyaIdleAnimation;//"PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaIdleSprite.scml";
    //Attack Animations
    public static String FAST_illyaOrbAttackAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaOrbAttackSprite.scml";
    public static String FAST_illyaBlastAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaBlastSprite.scml";
    public static String FAST_illyaSwipeAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaSwipeSprite.scml";
    //Defend Animation (Gain block)
    public static String FAST_illyaBlockAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaBlockSprite.scml";
    //Take Damage Animation
    public static String FAST_illyaAttackedAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaAttackedSprite.scml";
    public static String FAST_illyaBigAttackedAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaBigAttackedSprite.scml";
    //Channel Orb animation
    public static String FAST_illyaChannelAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaChannelSprite.scml";
    //Install Animation
    public static String FAST_illyaInstallAnimation = "PrismaModResources/images/char/Illya/IllyaSprite/FAST_IllyaInstallSprite.scml";*/




    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Prisma Mod";
    private static final String AUTHOR = "Beastly418"; // And pretty soon - You!
    private static final String DESCRIPTION = "Adds Illyasviel Von Einzbern to STS.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color ILLYA_PINK = new Color((float)235f / 255.0F, (float)174f / 255.0F, (float)192f / 255.0F, 1.0F);//CardHelper.getColor((int)235.0, (int)174.0, (int)192.0);   //Nadeshiko pink
    public static final Color COLOR_INSTALL = new Color((float)0 / 255.0F, (float)0 / 255.0F, (float)0 / 255.0F, 1.0F);//CardHelper.getColor(0,0,0);
    public static final Color CHLOE_RED = new Color((float)179f / 255.0F, (float)0 / 255.0F, (float)0 / 255.0F, 1.0F);//CardHelper.getColor((int)179.0, 0, 0);   //Mordant red
    public static final Color MIYU_BLUE = new Color((float)25f / 255.0F, (float)70f / 255.0F, (float)255f / 255.0F, 1.0F);//CardHelper.getColor((int)25.0f, (int)70.0f, (int)255.0f);



    /*// Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown*/

    //ILLYA STUFFS ==============================================================================
    private static final String ATTACK_ILLYA_PINK = "PrismaModResources/images/512/Illya/attack.png";
    private static final String SKILL_ILLYA_PINK = "PrismaModResources/images/512/Illya/skill.png";
    private static final String POWER_ILLYA_PINK = "PrismaModResources/images/512/Illya/power.png";

    private static final String ENERGY_ORB_ILLYA_PINK = "PrismaModResources/images/512/Illya/orb.png";
    private static final String CARD_ENERGY_ORB_ILLYA_PINK = "PrismaModResources/images/512/Illya/small_orb.png";

    private static final String ATTACK_ILLYA_PINK_PORTRAIT = "PrismaModResources/images/1024/Illya/attack.png";
    private static final String SKILL_ILLYA_PINK_PORTRAIT = "PrismaModResources/images/1024/Illya/skill.png";
    private static final String POWER_ILLYA_PINK_PORTRAIT = "PrismaModResources/images/1024/Illya/power.png";
    private static final String ENERGY_ORB_ILLYA_PINK_PORTRAIT = "PrismaModResources/images/1024/Illya/orb.png";

    //CHLOE STUFFS ==============================================================================
    private static final String ATTACK_CHLOE_RED = "PrismaModResources/images/512/Chloe/attack.png";
    private static final String SKILL_CHLOE_RED = "PrismaModResources/images/512/Chloe/skill.png";
    private static final String POWER_CHLOE_RED = "PrismaModResources/images/512/Chloe/power.png";

    private static final String ENERGY_ORB_CHLOE_RED = "PrismaModResources/images/512/Chloe/orb.png";
    private static final String CARD_ENERGY_ORB_CHLOE_RED = "PrismaModResources/images/512/Chloe/small_orb.png";

    private static final String ATTACK_CHLOE_RED_PORTRAIT = "PrismaModResources/images/1024/Chloe/attack.png";
    private static final String SKILL_CHLOE_RED_PORTRAIT = "PrismaModResources/images/1024/Chloe/skill.png";
    private static final String POWER_CHLOE_RED_PORTRAIT = "PrismaModResources/images/1024/Chloe/power.png";
    private static final String ENERGY_ORB_CHLOE_RED_PORTRAIT = "PrismaModResources/images/1024/Chloe/orb.png";

    //MIYU STUFFS ===============================================================================
    private static final String ATTACK_MIYU_BLUE = "PrismaModResources/images/512/Miyu/attack.png";
    private static final String SKILL_MIYU_BLUE = "PrismaModResources/images/512/Miyu/skill.png";
    private static final String POWER_MIYU_BLUE = "PrismaModResources/images/512/Miyu/power.png";

    private static final String ENERGY_ORB_MIYU_BLUE = "PrismaModResources/images/512/Miyu/orb.png";
    private static final String CARD_ENERGY_ORB_MIYU_BLUE = "PrismaModResources/images/512/Miyu/small_orb.png";

    private static final String ATTACK_MIYU_BLUE_PORTRAIT = "PrismaModResources/images/1024/Miyu/attack.png";
    private static final String SKILL_MIYU_BLUE_PORTRAIT = "PrismaModResources/images/1024/Miyu/skill.png";
    private static final String POWER_MIYU_BLUE_PORTRAIT = "PrismaModResources/images/1024/Miyu/power.png";
    private static final String ENERGY_ORB_MIYU_BLUE_PORTRAIT = "PrismaModResources/images/1024/Miyu/orb.png";

    //INSTALL CARD ==============================================================================

    public static final String INSTALL_BLANK = "PrismaModResources/images/BlankCardBackground.png";

    //ILLYA STUFFS ===========================================================================
    private static final String ILLYA_BUTTON = "PrismaModResources/images/charSelect/Illya/illyaButton.png";
    private static final String ILLYA_PORTRAIT = "PrismaModResources/images/charSelect/Illya/illyaPortraitNew.png";
    public static final String ILLYA_SHOULDER_1 = "PrismaModResources/images/char/Illya/shoulder.png";
    public static final String ILLYA_SHOULDER_2 = "PrismaModResources/images/char/Illya/shoulder2.png";
    public static final String ILLYA_CORPSE = "PrismaModResources/images/char/Illya/corpse.png";

    //CHLOE STUFFS ===========================================================================
    private static final String CHLOE_BUTTON = "PrismaModResources/images/charSelect/Chloe/chloeButton.png";
    private static final String CHLOE_PORTRAIT = "PrismaModResources/images/charSelect/Chloe/chloePortrait.png";
    public static final String CHLOE_SHOULDER_1 = "PrismaModResources/images/char/Chloe/shoulder.png";
    public static final String CHLOE_SHOULDER_2 = "PrismaModResources/images/char/Chloe/shoulder2.png";
    public static final String CHLOE_CORPSE = "PrismaModResources/images/char/Chloe/corpse.png";

    //MIYU STUFFS ===========================================================================
    private static final String MIYU_BUTTON = "PrismaModResources/images/charSelect/Miyu/miyuButton.png";
    private static final String MIYU_PORTRAIT = "PrismaModResources/images/charSelect/Miyu/miyuPortrait.png";
    public static final String MIYU_SHOULDER_1 = "PrismaModResources/images/char/Miyu/shoulder.png";
    public static final String MIYU_SHOULDER_2 = "PrismaModResources/images/char/Miyu/shoulder2.png";
    public static final String MIYU_CORPSE = "PrismaModResources/images/char/Miyu/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "PrismaModResources/images/Badge.png";


    private HashMap<String, Sfx> getSoundsMap() {
        return (HashMap) ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");// 115
    }

    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeSFXPath(String resourcePath) {
        return getModID() + "Resources/images/sfx/" + resourcePath;
    }

    public static String makeSoundPath(String resourcePath) {
        return getModID() + "Resources/images/sounds/" + resourcePath;
    }

    public static String makeMonsterAnimationPath(String resourcePath) {
        return getModID() + "Resources/images/monsters/" + resourcePath;
    }

    public static String makeMonsterSFXPath(String resourcePath) {
        return getModID() + "Resources/images/monsters/" + resourcePath;
    }

    public static String makeAnimPath(String resourcePath) {
        return getModID() + "Resources/images/char/" + resourcePath;
    }

    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public DefaultMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);

        setModID("PrismaMod");

        logger.info("Done subscribing");

        logger.info("Creating the color " + Illya.Enums.COLOR_ILLYA_PINK.toString());

        BaseMod.addColor(Illya.Enums.COLOR_ILLYA_PINK, ILLYA_PINK, ILLYA_PINK, ILLYA_PINK,
                ILLYA_PINK, ILLYA_PINK, ILLYA_PINK, ILLYA_PINK,
                ATTACK_ILLYA_PINK, SKILL_ILLYA_PINK, POWER_ILLYA_PINK, ENERGY_ORB_ILLYA_PINK,
                ATTACK_ILLYA_PINK_PORTRAIT, SKILL_ILLYA_PINK_PORTRAIT, POWER_ILLYA_PINK_PORTRAIT,
                ENERGY_ORB_ILLYA_PINK_PORTRAIT, CARD_ENERGY_ORB_ILLYA_PINK);

        logger.info("Done creating the color");

        logger.info("Creating the color " + Chloe.Enums.COLOR_CHLOE_RED.toString());

        BaseMod.addColor(Chloe.Enums.COLOR_CHLOE_RED, CHLOE_RED, CHLOE_RED, CHLOE_RED,
                CHLOE_RED, CHLOE_RED, CHLOE_RED, CHLOE_RED,
                ATTACK_CHLOE_RED, SKILL_CHLOE_RED, POWER_CHLOE_RED, ENERGY_ORB_CHLOE_RED,
                ATTACK_CHLOE_RED_PORTRAIT, SKILL_CHLOE_RED_PORTRAIT, POWER_CHLOE_RED_PORTRAIT,
                ENERGY_ORB_CHLOE_RED_PORTRAIT, CARD_ENERGY_ORB_CHLOE_RED);

        logger.info("Done creating the color");

        logger.info("Creating the color " + InstallColorEnum.COLOR_INSTALL.toString());


        BaseMod.addColor(InstallColorEnum.COLOR_INSTALL, COLOR_INSTALL, INSTALL_BLANK, INSTALL_BLANK,
                INSTALL_BLANK,INSTALL_BLANK, INSTALL_BLANK, INSTALL_BLANK, INSTALL_BLANK, INSTALL_BLANK, INSTALL_BLANK);

        logger.info("Done creating the color");

        logger.info("Creating the color " + Miyu.Enums.COLOR_MIYU_BLUE.toString());

        BaseMod.addColor(Miyu.Enums.COLOR_MIYU_BLUE, MIYU_BLUE, MIYU_BLUE, MIYU_BLUE,
                MIYU_BLUE, MIYU_BLUE, MIYU_BLUE, MIYU_BLUE,
                ATTACK_MIYU_BLUE, SKILL_MIYU_BLUE, POWER_MIYU_BLUE, ENERGY_ORB_MIYU_BLUE,
                ATTACK_MIYU_BLUE_PORTRAIT, SKILL_MIYU_BLUE_PORTRAIT, POWER_MIYU_BLUE_PORTRAIT,
                ENERGY_ORB_MIYU_BLUE_PORTRAIT, CARD_ENERGY_ORB_MIYU_BLUE);

        logger.info("Done creating the color");

        logger.info("Adding mod settings");





        //DONT TOUCH ANY OF THIS STUFF
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("PrismaMod", "prismaModConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Done adding mod settings");

    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = DefaultMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        DefaultMod defaultmod = new DefaultMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {

        logger.info("Beginning to edit characters. " + "Add " + Illya.Enums.ILLYA.toString());

        BaseMod.addCharacter(new Illya("Illya", Illya.Enums.ILLYA),
                ILLYA_BUTTON, ILLYA_PORTRAIT, Illya.Enums.ILLYA);

        logger.info("Added " + Illya.Enums.ILLYA.toString());

        logger.info("Add " + Chloe.Enums.CHLOE.toString());

        BaseMod.addCharacter(new Chloe("Chloe", Chloe.Enums.CHLOE),
                CHLOE_BUTTON, CHLOE_PORTRAIT, Chloe.Enums.CHLOE);

        logger.info("Added " + Chloe.Enums.CHLOE.toString());

        logger.info("Add " + Miyu.Enums.MIYU.toString());

        BaseMod.addCharacter(new Miyu("Miyu", Miyu.Enums.MIYU),
                MIYU_BUTTON, MIYU_PORTRAIT, Miyu.Enums.MIYU);

        logger.info("Added " + Miyu.Enums.MIYU.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    

    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);   //Dont touch
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();    //Dont touch
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Turn off animations.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("PrismaMod", "prismaModConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        logger.info("Done loading badge Image and mod options");

        // =============== MONSTERS =================
        logger.info("Adding Monsters");

        /*
        BaseMod.addMonster(Rider.ID, () -> new Rider());
        BaseMod.addMonster(SaberAlter.ID, () -> new SaberAlter());
        BaseMod.addMonster(Gilgamesh.ID, () -> new Gilgamesh());

        BaseMod.addBoss(TheBeyond.ID, Rider.ID,
                makeMonsterSFXPath("MedusaBossImage.png"),
                makeMonsterSFXPath("MedusaBossImage.png"));

        BaseMod.addBoss(TheBeyond.ID, SaberAlter.ID,
                makeMonsterSFXPath("SaberAlterBossImage.png"),
                makeMonsterSFXPath("SaberAlterBossImage.png"));

        BaseMod.addBoss(TheBeyond.ID, Gilgamesh.ID,
                makeMonsterSFXPath("GilgameshBossImage.png"),
                makeMonsterSFXPath("GilgameshBossImage.png"));
    */
        logger.info("Finished adding Monsters");
        // ============== /MONSTERS/ ================

        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== SOUNDS =================
        logger.info("Adding Sounds");
        HashMap<String, Sfx> reflectedMap = this.getSoundsMap();

        reflectedMap.put(getModID()+"ATTACK_1", new Sfx(makeSoundPath("Attack1.ogg"))); //Fire
        reflectedMap.put(getModID()+"ATTACK_2", new Sfx(makeSoundPath("Attack2.ogg"))); //Medium Attack
        reflectedMap.put(getModID()+"ATTACK_3", new Sfx(makeSoundPath("Attack3.ogg"))); //Big Attack
        reflectedMap.put(getModID()+"ATTACK_SELECT_1", new Sfx(makeSoundPath("Attack_Select1.ogg")));   //Illya Yes
        reflectedMap.put(getModID()+"ATTACK_SELECT_2", new Sfx(makeSoundPath("Attack_Select2.ogg")));   //Illya Yes
        reflectedMap.put(getModID()+"ATTACK_SELECT_3", new Sfx(makeSoundPath("Attack_Select3.ogg")));   //Illya Yes
        reflectedMap.put(getModID()+"BATTLE_FINISH_1", new Sfx(makeSoundPath("Battle_Finish1.ogg")));   //Boss beat
        reflectedMap.put(getModID()+"BATTLE_FINISH_2", new Sfx(makeSoundPath("Battle_Finish2.ogg")));   //Boss beat
        reflectedMap.put(getModID()+"BATTLE_START_1", new Sfx(makeSoundPath("Battle_Start1.ogg")));     //Boss Battle Start
        reflectedMap.put(getModID()+"BATTLE_START_2", new Sfx(makeSoundPath("Battle_Start2.ogg")));     //Boss Battle Start
        reflectedMap.put(getModID()+"SELECT", new Sfx(makeSoundPath("Bond4.ogg")));     //Sound that plays when you hover in select screen
        reflectedMap.put(getModID()+"DAMAGED", new Sfx(makeSoundPath("Damaged.ogg")));  //On Big attacked only
        reflectedMap.put(getModID()+"DEFEATED_1", new Sfx(makeSoundPath("Defeated1.ogg")));    //Long death
        reflectedMap.put(getModID()+"DEFEATED_2", new Sfx(makeSoundPath("Defeated2.ogg")));    //Onii chan
        reflectedMap.put(getModID()+"EXTRA_ATTACK", new Sfx(makeSoundPath("Extra_Attack.ogg")));    //Large angry hit
        reflectedMap.put(getModID()+"LEVEL_UP", new Sfx(makeSoundPath("Level_Up.ogg")));    //Unused probably
        reflectedMap.put(getModID()+"NP", new Sfx(makeSoundPath("NP.ogg")));    //Zwei Form Transformation will be used during the animation swap
        reflectedMap.put(getModID()+"NP_DAMAGED", new Sfx(makeSoundPath("NP_Damaged.ogg")));    //Unused
        reflectedMap.put(getModID()+"NP_SELECT", new Sfx(makeSoundPath("NP_Select.ogg")));      //Zwei Form Transformation?
        reflectedMap.put(getModID()+"SE_1", new Sfx(makeSoundPath("SE1.ogg"))); //Quick Charge
        reflectedMap.put(getModID()+"SE_2", new Sfx(makeSoundPath("SE2.ogg"))); //Quick Charge
        reflectedMap.put(getModID()+"SE_3", new Sfx(makeSoundPath("SE3.ogg"))); //Quick Blast
        reflectedMap.put(getModID()+"SE_4", new Sfx(makeSoundPath("SE4.ogg"))); //Quick Slash
        reflectedMap.put(getModID()+"SE_5", new Sfx(makeSoundPath("SE5.ogg"))); //Long Charge
        reflectedMap.put(getModID()+"SE_6", new Sfx(makeSoundPath("SE6.ogg"))); //Long Blast
        reflectedMap.put(getModID()+"SE_7", new Sfx(makeSoundPath("SE7.ogg"))); //Big Blast
        reflectedMap.put(getModID()+"SE_8", new Sfx(makeSoundPath("SE8.ogg"))); //Big Blast
        reflectedMap.put(getModID()+"SKILL_1", new Sfx(makeSoundPath("Skill1.ogg")));   //Illya Speak
        reflectedMap.put(getModID()+"SKILL_2", new Sfx(makeSoundPath("Skill2.ogg")));   //Ruby Speak
        reflectedMap.put(getModID()+"SCYTHE", new Sfx(makeSoundPath("Scythe.ogg")));

        reflectedMap.put(getModID()+"CHLOE_SELECT", new Sfx(makeSoundPath("Chloe_Select1.ogg")));
        reflectedMap.put(getModID()+"CHLOE_DEFEATED_1", new Sfx(makeSoundPath("Chloe_Defeated1.ogg")));    //Long death
        reflectedMap.put(getModID()+"CHLOE_DEFEATED_2", new Sfx(makeSoundPath("Chloe_Defeated2.ogg")));    //Onii chan

        logger.info("Finished Adding Sounds");
        // ============== /SOUNDS/ ================

        // =============== EVENTS =================

        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================


    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheDefault.Enums.THE_DEFAULT);

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    


    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelic(new PrismaModRubyRelic(), RelicType.SHARED);
        BaseMod.addRelic(new PrismaModAliceRelic(), RelicType.SHARED);
        BaseMod.addRelic(new PrismaModTraceOnRelic(), RelicType.SHARED);
        BaseMod.addRelic(new PrismaModZweiRelic(), RelicType.SHARED);
        BaseMod.addRelic(new PrismaModTraceOnUpgradeRelic(), RelicType.SHARED);
        BaseMod.addRelic(new PrismaModSaphireRelic(), RelicType.SHARED);
        BaseMod.addRelic(new PrismaModSaphirePlusRelic(), RelicType.SHARED);

        UnlockTracker.markRelicAsSeen(PrismaModRubyRelic.ID);
        UnlockTracker.markRelicAsSeen(PrismaModAliceRelic.ID);
        UnlockTracker.markRelicAsSeen(PrismaModTraceOnRelic.ID);
        UnlockTracker.markRelicAsSeen(PrismaModZweiRelic.ID);
        UnlockTracker.markRelicAsSeen(PrismaModTraceOnUpgradeRelic.ID);
        UnlockTracker.markRelicAsSeen(PrismaModSaphireRelic.ID);
        UnlockTracker.markRelicAsSeen(PrismaModSaphirePlusRelic.ID);
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        //UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");

        //ILLYA CARDS ==========================================================================
        //Install Cards
        BaseMod.addCard(new IllyaInstallAlterEgo());
        BaseMod.addCard(new IllyaInstallArcher());
        BaseMod.addCard(new IllyaInstallAvenger());
        BaseMod.addCard(new IllyaInstallAssassin());
        BaseMod.addCard(new IllyaInstallBerserker());
        BaseMod.addCard(new IllyaInstallCaster());
        BaseMod.addCard(new IllyaInstallForeigner());
        BaseMod.addCard(new IllyaInstallLancer());
        BaseMod.addCard(new IllyaInstallMoonCancer());
        BaseMod.addCard(new IllyaInstallRider());
        BaseMod.addCard(new IllyaInstallSaber());
        BaseMod.addCard(new IllyaInstallRuler());
        BaseMod.addCard(new IllyaInstallShielder());

        //Misc Cards
        BaseMod.addCard(new IllyaAlice());
        BaseMod.addCard(new IllyaBride());
        BaseMod.addCard(new IllyaCarnivalPrismaAstolfo());
        BaseMod.addCard(new IllyaCommandSeal());
        BaseMod.addCard(new IllyaDevil());
        BaseMod.addCard(new IllyaGrail());
        BaseMod.addCard(new IllyaGrailKun());
        BaseMod.addCard(new IllyaMaid());
        BaseMod.addCard(new IllyaMinorZwei());
        BaseMod.addCard(new IllyaRubysPotion());
        BaseMod.addCard(new IllyaZwei());

        //Normal Cards
        BaseMod.addCard(new IllyaAOECommonAttack());
        BaseMod.addCard(new IllyaBerserCAR());
        BaseMod.addCard(new IllyaCarnivalAttack());
        BaseMod.addCard(new IllyaCarnivalDefend());
        BaseMod.addCard(new IllyaCheapAttack());
        BaseMod.addCard(new IllyaCheapDefend());
        BaseMod.addCard(new IllyaCommonAttack());
        BaseMod.addCard(new IllyaCommonDefend());
        BaseMod.addCard(new IllyaGun());
        BaseMod.addCard(new IllyaPartialInstallAssassin());
        BaseMod.addCard(new IllyaUncommonAOEAttack());
        BaseMod.addCard(new IllyaUncommonAttack());
        BaseMod.addCard(new IllyaUncommonDefend());

        //Orb Cards
        BaseMod.addCard(new IllyaAssassinRevenge());
        BaseMod.addCard(new IllyaCarnivalOrbs());
        //BaseMod.addCard(new IllyaChanneledAttack());
        //BaseMod.addCard(new IllyaChanneledBlock());
        BaseMod.addCard(new IllyaCheapChannelAttack());
        BaseMod.addCard(new IllyaCheapChannelDefend());
        BaseMod.addCard(new IllyaCheapChannelPoison());
        BaseMod.addCard(new IllyaCommonEvoke());
        BaseMod.addCard(new IllyaDualEvoke());
        BaseMod.addCard(new IllyaEvokeAll());
        BaseMod.addCard(new IllyaHassanGift());
        BaseMod.addCard(new IllyaMinorRapidFire());
        BaseMod.addCard(new IllyaMinorReinForce());
        //BaseMod.addCard(new IllyaMultiEvoke());
        BaseMod.addCard(new IllyaRapidFire());
        BaseMod.addCard(new IllyaReinForce());
        BaseMod.addCard(new IllyaSaphire());
        BaseMod.addCard(new IllyaTestament());
        BaseMod.addCard(new IllyaUberAttackChannel());
        BaseMod.addCard(new IllyaUberChannel());
        BaseMod.addCard(new IllyaUberDefendChannel());
        BaseMod.addCard(new IllyaUberPoisonChannel());
        BaseMod.addCard(new IllyaUncommonEvoke());
        BaseMod.addCard(new IllyaUpgradeOrbs());

        //Poison Cards
        BaseMod.addCard(new IllyaBlindPoisonToss());
        BaseMod.addCard(new IllyaBoysLove());
        BaseMod.addCard(new IllyaDetonatingBlow());
        BaseMod.addCard(new IllyaFeed());
        BaseMod.addCard(new IllyaFriendMaker());
        BaseMod.addCard(new IllyaPoisonBlock());
        BaseMod.addCard(new IllyaPoisonBomb());
        BaseMod.addCard(new IllyaPoisoningPhantasm());
        BaseMod.addCard(new IllyaPoisonTippedWeapons());
        BaseMod.addCard(new IllyaRussianRoulette());
        BaseMod.addCard(new IllyaStunPoison());
        BaseMod.addCard(new IllyaUberPoisonBlock());



        logger.info("Making sure Illya cards are unlocked.");

        //ILLYA CARDS ===============================================================================
        //Install Cards
        UnlockTracker.unlockCard(IllyaInstallAlterEgo.ID);
        UnlockTracker.unlockCard(IllyaInstallArcher.ID);
        UnlockTracker.unlockCard(IllyaInstallAvenger.ID);
        UnlockTracker.unlockCard(IllyaInstallAssassin.ID);
        UnlockTracker.unlockCard(IllyaInstallBerserker.ID);
        UnlockTracker.unlockCard(IllyaInstallCaster.ID);
        UnlockTracker.unlockCard(IllyaInstallForeigner.ID);
        UnlockTracker.unlockCard(IllyaInstallLancer.ID);
        UnlockTracker.unlockCard(IllyaInstallMoonCancer.ID);
        UnlockTracker.unlockCard(IllyaInstallRider.ID);
        UnlockTracker.unlockCard(IllyaInstallSaber.ID);
        UnlockTracker.unlockCard(IllyaInstallShielder.ID);
        UnlockTracker.unlockCard(IllyaInstallRuler.ID);

        //Normal Cards
        UnlockTracker.unlockCard(IllyaAOECommonAttack.ID);
        UnlockTracker.unlockCard(IllyaBerserCAR.ID);
        UnlockTracker.unlockCard(IllyaCarnivalAttack.ID);
        UnlockTracker.unlockCard(IllyaCarnivalDefend.ID);
        UnlockTracker.unlockCard(IllyaCheapAttack.ID);
        UnlockTracker.unlockCard(IllyaCheapDefend.ID);
        UnlockTracker.unlockCard(IllyaCommonAttack.ID);
        UnlockTracker.unlockCard(IllyaCommonDefend.ID);
        UnlockTracker.unlockCard(IllyaGun.ID);
        UnlockTracker.unlockCard(IllyaPartialInstallAssassin.ID);
        UnlockTracker.unlockCard(IllyaUncommonAOEAttack.ID);
        UnlockTracker.unlockCard(IllyaUncommonAttack.ID);
        UnlockTracker.unlockCard(IllyaUncommonDefend.ID);

        //Orbs Cards
        UnlockTracker.unlockCard(IllyaAssassinRevenge.ID);
        UnlockTracker.unlockCard(IllyaCarnivalOrbs.ID);
        //UnlockTracker.unlockCard(IllyaChanneledAttack.ID);
        //UnlockTracker.unlockCard(IllyaChanneledBlock.ID);
        UnlockTracker.unlockCard(IllyaCheapChannelAttack.ID);
        UnlockTracker.unlockCard(IllyaCheapChannelDefend.ID);
        UnlockTracker.unlockCard(IllyaCheapChannelPoison.ID);
        UnlockTracker.unlockCard(IllyaCommonEvoke.ID);
        UnlockTracker.unlockCard(IllyaDualEvoke.ID);
        UnlockTracker.unlockCard(IllyaEvokeAll.ID);
        UnlockTracker.unlockCard(IllyaHassanGift.ID);
        UnlockTracker.unlockCard(IllyaMinorRapidFire.ID);
        UnlockTracker.unlockCard(IllyaMinorReinForce.ID);
        //UnlockTracker.unlockCard(IllyaMultiEvoke.ID);
        UnlockTracker.unlockCard(IllyaRapidFire.ID);
        UnlockTracker.unlockCard(IllyaReinForce.ID);
        UnlockTracker.unlockCard(IllyaSaphire.ID);
        UnlockTracker.unlockCard(IllyaTestament.ID);
        UnlockTracker.unlockCard(IllyaUberAttackChannel.ID);
        UnlockTracker.unlockCard(IllyaUberChannel.ID);
        UnlockTracker.unlockCard(IllyaUberDefendChannel.ID);
        UnlockTracker.unlockCard(IllyaUberPoisonChannel.ID);
        UnlockTracker.unlockCard(IllyaUncommonEvoke.ID);
        UnlockTracker.unlockCard(IllyaUpgradeOrbs.ID);


        //Misc Cards
        UnlockTracker.unlockCard(IllyaAlice.ID);
        UnlockTracker.unlockCard(IllyaBride.ID);
        UnlockTracker.unlockCard(IllyaCarnivalPrismaAstolfo.ID);
        UnlockTracker.unlockCard(IllyaCommandSeal.ID);
        UnlockTracker.unlockCard(IllyaDevil.ID);
        UnlockTracker.unlockCard(IllyaGrail.ID);
        UnlockTracker.unlockCard(IllyaGrailKun.ID);
        UnlockTracker.unlockCard(IllyaMaid.ID);
        UnlockTracker.unlockCard(IllyaMinorZwei.ID);
        UnlockTracker.unlockCard(IllyaRubysPotion.ID);
        UnlockTracker.unlockCard(IllyaZwei.ID);


        //Poison Cards
        UnlockTracker.unlockCard(IllyaBlindPoisonToss.ID);
        UnlockTracker.unlockCard(IllyaBoysLove.ID);
        UnlockTracker.unlockCard(IllyaDetonatingBlow.ID);
        UnlockTracker.unlockCard(IllyaFeed.ID);
        UnlockTracker.unlockCard(IllyaFriendMaker.ID);
        UnlockTracker.unlockCard(IllyaPoisonBlock.ID);
        UnlockTracker.unlockCard(IllyaPoisonBomb.ID);
        UnlockTracker.unlockCard(IllyaPoisoningPhantasm.ID);
        UnlockTracker.unlockCard(IllyaPoisonTippedWeapons.ID);
        UnlockTracker.unlockCard(IllyaRussianRoulette.ID);
        UnlockTracker.unlockCard(IllyaStunPoison.ID);
        UnlockTracker.unlockCard(IllyaUberPoisonBlock.ID);

        //CHLOE CARDS ============================================================================================

        logger.info("Creating Chloe cards");

        //Trace
        BaseMod.addCard(new ChloeCreateableTraceCard());
        BaseMod.addCard(new ChloeAnyAttackTrace());
        BaseMod.addCard(new ChloeAnyCommonTrace());
        BaseMod.addCard(new ChloeAnyPowerTrace());
        BaseMod.addCard(new ChloeAnyRareTrace());
        BaseMod.addCard(new ChloeAnySkillTrace());
        BaseMod.addCard(new ChloeAnyUncommonTrace());

        //Misc
        BaseMod.addCard(new ChloeChristmas());
        BaseMod.addCard(new ChloeDevil());
        BaseMod.addCard(new ChloeHalloween());
        BaseMod.addCard(new ChloeMaid());
        BaseMod.addCard(new ChloeMordred());
        BaseMod.addCard(new ChloeRitsuka());
        BaseMod.addCard(new ChloeUnlimitedBladeWorks());
        BaseMod.addCard(new ChloeDemonicTutor());
        BaseMod.addCard(new ChloeDiscardRecovery());
        BaseMod.addCard(new ChloeReshuffleDiscard());

        //Normal
        BaseMod.addCard(new ChloeCarnivalAOEAttack());
        BaseMod.addCard(new ChloeCarnivalAttack());
        BaseMod.addCard(new ChloeCarnivalDefend());
        BaseMod.addCard(new ChloeCommonAOEAttack());
        BaseMod.addCard(new ChloeCommonAttack());
        BaseMod.addCard(new ChloeCommonDefend());
        BaseMod.addCard(new ChloeUncommonAOEAttack());
        BaseMod.addCard(new ChloeUncommonAttack());
        BaseMod.addCard(new ChloeUncommonDefend());
        BaseMod.addCard(new ChloeCheapAttack());
        BaseMod.addCard(new ChloeCheapAttackTwo());
        BaseMod.addCard(new ChloeCheapDefend());
        BaseMod.addCard(new ChloeCheapDefendTwo());
        BaseMod.addCard(new ChloeShiv());

        //Special
        BaseMod.addCard(new ChloeCaladbolg());
        BaseMod.addCard(new ChloeExcalibur());
        BaseMod.addCard(new ChloeEyeOfTheEuryale());
        BaseMod.addCard(new ChloeGaeBolg());
        //BaseMod.addCard(new ChloePhantasmPunishment());
        BaseMod.addCard(new ChloeRhongomyniad());
        BaseMod.addCard(new ChloeRuleBreaker());

        //Exhaust
        BaseMod.addCard(new ChloeExhaustOne());
        BaseMod.addCard(new ChloeExhaustTwo());
        BaseMod.addCard(new ChloeExhaustThree());

        //RelicBuff
        BaseMod.addCard(new ChloeBetterTracePools());
        BaseMod.addCard(new ChloeDoubleRelicSpeed());
        BaseMod.addCard(new ChloeGreatTracePools());

        logger.info("Making sure Chloe cards are unlocked");

        //Trace
        UnlockTracker.unlockCard(ChloeCreateableTraceCard.ID);
        UnlockTracker.unlockCard(ChloeAnyAttackTrace.ID);
        UnlockTracker.unlockCard(ChloeAnyCommonTrace.ID);
        UnlockTracker.unlockCard(ChloeAnyPowerTrace.ID);
        UnlockTracker.unlockCard(ChloeAnyRareTrace.ID);
        UnlockTracker.unlockCard(ChloeAnySkillTrace.ID);
        UnlockTracker.unlockCard(ChloeAnyUncommonTrace.ID);

        //Misc
        UnlockTracker.unlockCard(ChloeChristmas.ID);
        UnlockTracker.unlockCard(ChloeDevil.ID);
        UnlockTracker.unlockCard(ChloeHalloween.ID);
        UnlockTracker.unlockCard(ChloeMaid.ID);
        UnlockTracker.unlockCard(ChloeMordred.ID);
        UnlockTracker.unlockCard(ChloeRitsuka.ID);
        UnlockTracker.unlockCard(ChloeUnlimitedBladeWorks.ID);
        UnlockTracker.unlockCard(ChloeDemonicTutor.ID);
        UnlockTracker.unlockCard(ChloeDiscardRecovery.ID);
        UnlockTracker.unlockCard(ChloeReshuffleDiscard.ID);

        //Normal
        UnlockTracker.unlockCard(ChloeCarnivalAOEAttack.ID);
        UnlockTracker.unlockCard(ChloeCarnivalAttack.ID);
        UnlockTracker.unlockCard(ChloeCarnivalDefend.ID);
        UnlockTracker.unlockCard(ChloeCommonAOEAttack.ID);
        UnlockTracker.unlockCard(ChloeCommonAttack.ID);
        UnlockTracker.unlockCard(ChloeCommonDefend.ID);
        UnlockTracker.unlockCard(ChloeUncommonAOEAttack.ID);
        UnlockTracker.unlockCard(ChloeUncommonAttack.ID);
        UnlockTracker.unlockCard(ChloeUncommonDefend.ID);
        UnlockTracker.unlockCard(ChloeCheapAttack.ID);
        UnlockTracker.unlockCard(ChloeCheapAttackTwo.ID);
        UnlockTracker.unlockCard(ChloeCheapDefend.ID);
        UnlockTracker.unlockCard(ChloeCheapDefendTwo.ID);
        UnlockTracker.unlockCard(ChloeShiv.ID);

        //Special
        UnlockTracker.unlockCard(ChloeCaladbolg.ID);
        UnlockTracker.unlockCard(ChloeExcalibur.ID);
        UnlockTracker.unlockCard(ChloeEyeOfTheEuryale.ID);
        UnlockTracker.unlockCard(ChloeGaeBolg.ID);
        //UnlockTracker.unlockCard(ChloePhantasmPunishment.ID);
        UnlockTracker.unlockCard(ChloeRhongomyniad.ID);
        UnlockTracker.unlockCard(ChloeRuleBreaker.ID);

        //Exhaust
        UnlockTracker.unlockCard(ChloeExhaustOne.ID);
        UnlockTracker.unlockCard(ChloeExhaustTwo.ID);
        UnlockTracker.unlockCard(ChloeExhaustThree.ID);

        //RelicBuff
        UnlockTracker.unlockCard(ChloeBetterTracePools.ID);
        UnlockTracker.unlockCard(ChloeDoubleRelicSpeed.ID);
        UnlockTracker.unlockCard(ChloeGreatTracePools.ID);


        //Miyu Cards ============================================================================================

        //Misc
        BaseMod.addCard(new MiyuBride());
        BaseMod.addCard(new MiyuChristmas());
        BaseMod.addCard(new MiyuDevil());
        BaseMod.addCard(new MiyuMaid());

        //Normal
        //BaseMod.addCard(new MiyuCarnivalAOEAttack());
        BaseMod.addCard(new MiyuCarnivalAttack());
        BaseMod.addCard(new MiyuCarnivalDefend());
        BaseMod.addCard(new MiyuCheapAttack1());
        BaseMod.addCard(new MiyuCheapAttack2());
        BaseMod.addCard(new MiyuCheapDefend1());
        BaseMod.addCard(new MiyuCheapDefend2());
        BaseMod.addCard(new MiyuCheapDexBuff());
        BaseMod.addCard(new MiyuCheapPlate());
        BaseMod.addCard(new MiyuCheapStrBuff());
        BaseMod.addCard(new MiyuCommonAOEAttack());
        BaseMod.addCard(new MiyuCommonAttack());
        BaseMod.addCard(new MiyuCommonDefend());
        BaseMod.addCard(new MiyuDefenseAbsorbMinor());
        //BaseMod.addCard(new MiyuDefenseAbsorbUber());
        BaseMod.addCard(new MiyuDefenseExplosion());
        BaseMod.addCard(new MiyuDefenseRemoval());
        BaseMod.addCard(new MiyuUberAOEAttack());
        BaseMod.addCard(new MiyuUberAttack());
        BaseMod.addCard(new MiyuUberDefend());
        BaseMod.addCard(new MiyuUncommonAOEAttack());
        BaseMod.addCard(new MiyuUncommonAttack());
        BaseMod.addCard(new MiyuUncommonDefend());
        BaseMod.addCard(new MiyuUncommonDexBuff());
        BaseMod.addCard(new MiyuUncommonPlate());
        BaseMod.addCard(new MiyuUncommonStrBuff());

        //Orbs
        BaseMod.addCard(new MiyuAttackChannelAttack());
        BaseMod.addCard(new MiyuAttackChannelVul());
        BaseMod.addCard(new MiyuAttackChannelWeak());
        BaseMod.addCard(new MiyuChannelAttackEvoke());
        BaseMod.addCard(new MiyuChannelDefendEvoke());
        //BaseMod.addCard(new MiyuChannelHealEvoke());
        //BaseMod.addCard(new MiyuChannelRandomEvoke());
        //BaseMod.addCard(new MiyuChannelVulEvoke());
        //BaseMod.addCard(new MiyuChannelWeakEvoke());
        BaseMod.addCard(new MiyuCheapChannelAttack());
        BaseMod.addCard(new MiyuCheapChannelDefend());
        BaseMod.addCard(new MiyuCheapChannelHeal());
        BaseMod.addCard(new MiyuCheapChannelRandom());
        BaseMod.addCard(new MiyuCheapChannelVul());
        BaseMod.addCard(new MiyuCheapChannelWeak());
        BaseMod.addCard(new MiyuCheapEvoke());
        BaseMod.addCard(new MiyuDefendChannelDefend());
        BaseMod.addCard(new MiyuEvokeAll());
        BaseMod.addCard(new MiyuEvokeAllAttack());
        BaseMod.addCard(new MiyuEvokeAllDefend());
        BaseMod.addCard(new MiyuEvokeBuffDex());
        BaseMod.addCard(new MiyuEvokeBuffStr());
        BaseMod.addCard(new MiyuEvokeCheapAttack());
        BaseMod.addCard(new MiyuEvokeCheapDefend());
        BaseMod.addCard(new MiyuOrbIncreasePerm());
        //BaseMod.addCard(new MiyuOrbIncreaseTemp());


        //Misc
        UnlockTracker.unlockCard(MiyuBride.ID);
        UnlockTracker.unlockCard(MiyuChristmas.ID);
        UnlockTracker.unlockCard(MiyuDevil.ID);
        UnlockTracker.unlockCard(MiyuMaid.ID);

        //Normal
        //UnlockTracker.unlockCard(MiyuCarnivalAOEAttack.ID);
        UnlockTracker.unlockCard(MiyuCarnivalAttack.ID);
        UnlockTracker.unlockCard(MiyuCarnivalDefend.ID);
        UnlockTracker.unlockCard(MiyuCheapAttack1.ID);
        UnlockTracker.unlockCard(MiyuCheapAttack2.ID);
        UnlockTracker.unlockCard(MiyuCheapDefend1.ID);
        UnlockTracker.unlockCard(MiyuCheapDefend2.ID);
        UnlockTracker.unlockCard(MiyuCheapDexBuff.ID);
        UnlockTracker.unlockCard(MiyuCheapPlate.ID);
        UnlockTracker.unlockCard(MiyuCheapStrBuff.ID);
        UnlockTracker.unlockCard(MiyuCommonAOEAttack.ID);
        UnlockTracker.unlockCard(MiyuCommonAttack.ID);
        UnlockTracker.unlockCard(MiyuCommonDefend.ID);
        UnlockTracker.unlockCard(MiyuDefenseAbsorbMinor.ID);
        //UnlockTracker.unlockCard(MiyuDefenseAbsorbUber.ID);
        UnlockTracker.unlockCard(MiyuDefenseExplosion.ID);
        UnlockTracker.unlockCard(MiyuDefenseRemoval.ID);
        UnlockTracker.unlockCard(MiyuUberAOEAttack.ID);
        UnlockTracker.unlockCard(MiyuUberAttack.ID);
        UnlockTracker.unlockCard(MiyuUberDefend.ID);
        UnlockTracker.unlockCard(MiyuUncommonAOEAttack.ID);
        UnlockTracker.unlockCard(MiyuUncommonAttack.ID);
        UnlockTracker.unlockCard(MiyuUncommonDefend.ID);
        UnlockTracker.unlockCard(MiyuUncommonDexBuff.ID);
        UnlockTracker.unlockCard(MiyuUncommonPlate.ID);
        UnlockTracker.unlockCard(MiyuUncommonStrBuff.ID);

        //Orbs
        UnlockTracker.unlockCard(MiyuAttackChannelAttack.ID);
        UnlockTracker.unlockCard(MiyuAttackChannelVul.ID);
        UnlockTracker.unlockCard(MiyuAttackChannelWeak.ID);
        UnlockTracker.unlockCard(MiyuChannelAttackEvoke.ID);
        UnlockTracker.unlockCard(MiyuChannelDefendEvoke.ID);
        //UnlockTracker.unlockCard(MiyuChannelHealEvoke.ID);
        //UnlockTracker.unlockCard(MiyuChannelRandomEvoke.ID);
        //UnlockTracker.unlockCard(MiyuChannelVulEvoke.ID);
        //UnlockTracker.unlockCard(MiyuChannelWeakEvoke.ID);
        UnlockTracker.unlockCard(MiyuCheapChannelAttack.ID);
        UnlockTracker.unlockCard(MiyuCheapChannelDefend.ID);
        UnlockTracker.unlockCard(MiyuCheapChannelHeal.ID);
        UnlockTracker.unlockCard(MiyuCheapChannelRandom.ID);
        UnlockTracker.unlockCard(MiyuCheapChannelVul.ID);
        UnlockTracker.unlockCard(MiyuCheapChannelWeak.ID);
        UnlockTracker.unlockCard(MiyuCheapEvoke.ID);
        UnlockTracker.unlockCard(MiyuDefendChannelDefend.ID);
        UnlockTracker.unlockCard(MiyuEvokeAll.ID);
        UnlockTracker.unlockCard(MiyuEvokeAllAttack.ID);
        UnlockTracker.unlockCard(MiyuEvokeAllDefend.ID);
        UnlockTracker.unlockCard(MiyuEvokeBuffDex.ID);
        UnlockTracker.unlockCard(MiyuEvokeBuffStr.ID);
        UnlockTracker.unlockCard(MiyuEvokeCheapAttack.ID);
        UnlockTracker.unlockCard(MiyuEvokeCheapDefend.ID);
        UnlockTracker.unlockCard(MiyuOrbIncreasePerm.ID);
        //UnlockTracker.unlockCard(MiyuOrbIncreaseTemp.ID);


        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================


    
    // ================ LOAD THE TEXT ===================
    //public static Settings.GameLanguage lang = Settings.language;
    //  Get language

    public static String getLanguageString(Settings.GameLanguage language) {
        switch (language) {
            case EPO:
                return "ptb";
            case JPN:
                return "jp";
            case KOR:
                return "kor";
            case ZHS:
                return "zhs";
            default:
                return "eng";
        }
    }

    private static final String resLocal = "Resources/localization/";
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        final String lang = getLanguageString(Settings.language);
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                getModID() + resLocal + lang + "/PrismaMod-Monsters-Strings.json");
        
        logger.info("Done editing strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        final String lang = getLanguageString(Settings.language);

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + resLocal + lang + "/PrismaMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}

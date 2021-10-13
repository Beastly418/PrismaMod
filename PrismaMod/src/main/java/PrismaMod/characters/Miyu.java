package PrismaMod.characters;

import PrismaMod.cards.ChloeCards.Normal.*;
import PrismaMod.cards.ChloeCards.Special.ChloeExcalibur;
import PrismaMod.cards.ChloeCards.Special.ChloeRhongomyniad;
import PrismaMod.cards.IllyaCards.Normal.IllyaCommonAttack;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.*;
import PrismaMod.cards.IllyaCards.Normal.IllyaCommonDefend;
import PrismaMod.cards.IllyaCards.Normal.IllyaUncommonAttack;
import PrismaMod.cards.IllyaCards.Normal.IllyaUncommonDefend;
import PrismaMod.cards.MiyuCards.Normal.MiyuCommonAttack;
import PrismaMod.cards.MiyuCards.Normal.MiyuCommonDefend;
import PrismaMod.cards.MiyuCards.Orbs.MiyuCheapChannelAttack;
import PrismaMod.cards.MiyuCards.Orbs.MiyuCheapChannelDefend;
import PrismaMod.cards.MiyuCards.Orbs.MiyuCheapEvoke;
import PrismaMod.cards.MiyuCards.Orbs.MiyuOrbIncreasePerm;
import PrismaMod.characters.Animation.AbstractPrismaPlayerAnim;
import PrismaMod.relics.PrismaModRubyRelic;
import PrismaMod.relics.PrismaModSaphireRelic;
import PrismaMod.relics.PrismaModTraceOnRelic;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpriterAnimation;
import basemod.interfaces.RelicGetSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PrismaMod.DefaultMod;

import java.util.ArrayList;
import java.util.Arrays;

import static PrismaMod.DefaultMod.*;
import static PrismaMod.patches.AttackEffectEnum.*;

public class Miyu extends AbstractPrismaPlayer {
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

    public MiyuAnimHandler animationHandler;

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass MIYU;
        @SpireEnum(name = "MIYU_BLUE") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_MIYU_BLUE;
        @SpireEnum(name = "MIYU_BLUE") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 3;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("Miyu");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "PrismaModResources/images/char/Miyu/orb/layer1.png",
            "PrismaModResources/images/char/Miyu/orb/layer2.png",
            "PrismaModResources/images/char/Miyu/orb/layer3.png",
            "PrismaModResources/images/char/Miyu/orb/layer4.png",
            "PrismaModResources/images/char/Miyu/orb/layer5.png",
            "PrismaModResources/images/char/Miyu/orb/layer6.png",
            "PrismaModResources/images/char/Miyu/orb/layer1d.png",
            "PrismaModResources/images/char/Miyu/orb/layer2d.png",
            "PrismaModResources/images/char/Miyu/orb/layer3d.png",
            "PrismaModResources/images/char/Miyu/orb/layer4d.png",
            "PrismaModResources/images/char/Miyu/orb/layer5d.png",};


    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================
    private static String orbVFX = "PrismaModResources/images/char/Miyu/orb/vfx.png";
    public Miyu(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, orbVFX, null, new SpriterAnimation(blank));

        this.animationPaths[0] = makeAnimPath("Miyu/Anim/AttackOne.png");
        this.animationPaths[1] = makeAnimPath("Miyu/Anim/AttackTwo.png");
        this.animationPaths[2] = makeAnimPath("Miyu/Anim/SkillOne.png");
        this.animationPaths[3] = makeAnimPath("Miyu/Anim/SkillTwo.png");
        this.animationPaths[4] = makeAnimPath("Miyu/Anim/SkillThree.png");
        this.animationPaths[5] = makeAnimPath("Miyu/Anim/Damaged.png");
        this.animationPaths[6] = makeAnimPath("Miyu/Anim/BigDamaged.png");
        this.animationPaths[7] = makeAnimPath("Miyu/Anim/Idle.png");

        for(String s : animationPaths) {
            try {
                animations.add(new AbstractPrismaPlayerAnim(s, spriteWidth, spriteHeight, (int)drawX, (int)drawY, fps, loop, Settings.scale*.5F, Settings.scale*.5F));
                logger.info("added animation" + s);
            } catch(java.lang.NullPointerException e) {
                break;
            }
        }
        animations.get(7).setLoop(true);
        //animations.get(0).setLoop(true);

        animationHandler = new MiyuAnimHandler(this, animations);
        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                MIYU_SHOULDER_1, // campfire pose
                MIYU_SHOULDER_2, // another campfire pose
                MIYU_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================



        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        int com = 5;

        logger.info("Begin loading starter Deck Strings");
        for(int i = 0; i < com; i++)
            retVal.add(MiyuCommonDefend.ID);
        for(int i = 0; i < com; i++)
            retVal.add(MiyuCommonAttack.ID);

        retVal.add(MiyuCheapChannelAttack.ID);
        retVal.add(MiyuCheapChannelDefend.ID);
        //retVal.add(MiyuCheapEvoke.ID);


        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(PrismaModSaphireRelic.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(getModID()+"CHLOE_SELECT", 0.0F); // Sound Effect

        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return getModID()+"NP_DAMAGED";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.COLOR_MIYU_BLUE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return MIYU_BLUE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new MiyuOrbIncreasePerm();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new Miyu(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return MIYU_BLUE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return MIYU_BLUE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public void damage(DamageInfo info) {
        super.damage(info);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        animationHandler.anim.render(sb);
    }

    @Override
    public void update() {
        super.update();
        animationHandler.update();
    }

    @Override
    public void playDeathAnimation() {
        super.playDeathAnimation();
        double rand = Math.random();
        if (rand < .5){
            CardCrawlGame.sound.play(getModID()+"CHLOE_DEFEATED_1");
        } else {
            CardCrawlGame.sound.play(getModID()+"CHLOE_DEFEATED_2");
        }
    }
    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {

    }

    @Override
    public void useFastAttackAnimation() {
        if (!enablePlaceholder && false)
            return;
        super.useFastAttackAnimation();
    }

    @Override
    public void useSlowAttackAnimation() {
        if (!enablePlaceholder && false)
            return;
        super.useSlowAttackAnimation();
    }

    @Override
    public void useFastShakeAnimation(float duration) {
        if (!enablePlaceholder && false)
            return;
        super.useFastShakeAnimation(duration);
    }

    @Override
    public void useShakeAnimation(float duration) {
        if (!enablePlaceholder && false)
            return;
        super.useShakeAnimation(duration);
    }

    public class MiyuAnimHandler extends AbstractPrismaPlayerHandler {
        MiyuAnimHandler(AbstractPrismaPlayer owner, ArrayList<AbstractPrismaPlayerAnim> animationList) {
            super(owner, animationList);
        }

        @Override
        public AbstractPrismaPlayerAnim getAnimation(PlayerAnimationEnums animation) {
            switch(animation) {
                case ATTACK_ONE:
                    return animationList.get(0);
                case ATTACK_TWO:
                    return animationList.get(1);
                case SKILL_ONE:
                    return animationList.get(2);
                case SKILL_TWO:
                    return animationList.get(3);
                case SKILL_THREE:
                    return animationList.get(4);
                case DAMAGED:
                    return animationList.get(5);
                case BIG_DAMAGED:
                    return animationList.get(6);
                case IDLE:
                    return animationList.get(7);
                default:
                    logger.info("Animation not found");
                    return null;
            }
        }
    }
}


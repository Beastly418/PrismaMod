package PrismaMod.orbs;

//Evoke throw 3 random effects
//Passive throw 1 random effect

import PrismaMod.cards.ChloeCards.Normal.ChloeShiv;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static PrismaMod.DefaultMod.makeOrbPath;

public class MiyuRandomOrb extends AbstractOrb {
    public static final Logger logger = LogManager.getLogger(MiyuRandomOrb.class.getName());

    // Standard ID/Description
    public static final String ORB_ID = DefaultMod.makeID("MiyuRandomOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    private static final Texture IMG = TextureLoader.getTexture(makeOrbPath("FleurDiamond.png"));
    //private static final Texture IMG2 = TextureLoader.getTexture(makeOrbPath("FleurDiamond.png"));
    //private Texture img2 = IMG2;
    //private Texture[] imgArray = new Texture[2];

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    private float colorCounter = 0.33f;
    private float colorCounter2 = 0.66f;
    private float colorCounter3 = 0.99f;
    private boolean up = true;
    private boolean up1 = true;
    private boolean up2 = true;

    public MiyuRandomOrb() {

        ID = ORB_ID;
        name = orbString.NAME;
        img = IMG;
        //imgArray[0] = img;
        //imgArray[1] = img2;

        passiveAmount = basePassiveAmount = 1;
        evokeAmount = baseEvokeAmount = 3;
        updateDescription();

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        applyFocus(); // Apply Focus (Look at the next method)
        description = DESC[0] + passiveAmount + DESC[1] + DESC[2] + evokeAmount + DESC[3]; // Set the description
    }

    @Override
    public void applyFocus() {
        if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
            //this.evokeAmount = 4 + (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount-1);
            this.passiveAmount = 2 + (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount-1);
        } else {
            passiveAmount = basePassiveAmount = 1;
            evokeAmount = baseEvokeAmount = 3;
        }
    }

    private void randomEffect() {
        int rand = (int) (Math.random()*11);
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        switch(rand) {
            case(0):    //chloe shiv
                AbstractCard c = new ChloeShiv();
                c.retain = true;
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c, 1));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[4] + 1 + DESC[5], 2.0F, 2.0F));
                break;
            case(1):    //Deal damage
                int randDam = (int)(Math.random()*5) + 2;
                DamageInfo info = new DamageInfo(m, randDam);

                AbstractDungeon.actionManager.addToTop(new DamageAction(m, info));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[6] + randDam + DESC[7], 2.0F, 2.0F));
                break;
            case(2):    //Gain Block
                int randB = (int)(Math.random()*5) + 2;
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, randB));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[8] + randB + DESC[9], 2.0F, 2.0F));
                break;
            case(3):    //Apply vulnerable
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new VulnerablePower(m, 1, false)));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[10] + 1 + DESC[11], 2.0F, 2.0F));
                break;
            case(4):    //Apply weak
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new WeakPower(m, 1, false)));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[12] + 1 + DESC[13], 2.0F, 2.0F));
                break;
            case(5):    //Temporary retain a card in hand
                AbstractDungeon.actionManager.addToTop(new RetainCardsAction(AbstractDungeon.player, 1));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[14] + 1 + DESC[15], 2.0F, 2.0F));
                break;
            case(6):    //Gain energy
                AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[16] + 1 + DESC[17], 2.0F, 2.0F));
                break;
            case(7):    //Draw 1 card
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[18] + 2 + DESC[19], 2.0F, 2.0F));
                break;
            case(8):    //Deal Damage to self
                int randDamS = (int)(Math.random()*2) + 2;
                DamageInfo infoz = new DamageInfo(AbstractDungeon.player, randDamS);

                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, infoz));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[20] + randDamS + DESC[21], 2.0F, 2.0F));
                break;
            case(9):    //Heal enemy
                int randHeal = (int)(Math.random()*5) + 3;
                AbstractDungeon.actionManager.addToTop(new HealAction(m, AbstractDungeon.player, randHeal));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[22] + randHeal + DESC[23], 2.0F, 2.0F));
                break;
            case(10):   //Gain a temporary curse
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(AbstractDungeon.returnRandomCurse()));
                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[24] + 1 + DESC[25], 2.0F, 2.0F));
                break;
            default:    //Say WHAT I BROKE THE DICE HOW!?!?

                AbstractDungeon.actionManager.addToTop(new TalkAction(true, DESC[26], 2.0F, 2.0F));
                break;
        }
    }

    @Override
    public void onEvoke() {


        for(int i = 0; i < this.evokeAmount; i++){
            this.randomEffect();
        }


        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA"));
    }

    @Override
    public void onStartOfTurn() {
        for(int i = 0; i < this.passiveAmount; i++){
            this.randomEffect();
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f));


    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(cX, cY)); // This is the purple-sparkles in the orb. You can change this to whatever fits your orb.
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
        if(up) {
            colorCounter = colorCounter + .005F;
        } else {
            colorCounter = colorCounter - .005F;
        }
        if(up1) {
            colorCounter2 = colorCounter2 + .005F;
        } else {
            colorCounter2 = colorCounter2 - .005F;
        }
        if(up2) {
            colorCounter3 = colorCounter3 + .005F;
        } else {
            colorCounter3 = colorCounter3 - .005F;
        }

        if(colorCounter >= 1 && up) {
            up = false;
        } else if(colorCounter <= 0 && !up) {
            up = true;
        }
        if(colorCounter2 >= 1 && up1) {
            up1 = false;
        } else if(colorCounter2 <= 0 && !up1) {
            up1 = true;
        }
        if(colorCounter3 >= 1 && up2) {
            up2 = false;
        } else if(colorCounter3 <= 0 && !up2) {
            up2 = true;
        }
        //logger.info("colorCounter: " + colorCounter);
        //logger.info("color: " + color);
    }

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {

        float randX = (float) (Math.random()*.5);
        float randY = (float) (Math.random()*.5);


        sb.setColor(new Color(1.0f*colorCounter, 1.0f*colorCounter2, 1.0f*colorCounter3, c.a / 2.0f));
        sb.draw(img, cX - 48.0f + 3*randX, cY - 48.0f + bobEffect.y + 3*randY, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);

    }


    @Override
    public void triggerEvokeAnimation() { // The evoke animation of this orb is the dark-orb activation effect.
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new MiyuRandomOrb();
    }
}


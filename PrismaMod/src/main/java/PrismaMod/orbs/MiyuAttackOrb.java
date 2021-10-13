package PrismaMod.orbs;

import PrismaMod.powers.Illya.IllyaCasterPower;
import PrismaMod.powers.Illya.IllyaForeignerPower;
import PrismaMod.powers.Illya.IllyaMoonCancerPower;
import PrismaMod.powers.UpgradeOrbsPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;

import static PrismaMod.DefaultMod.makeOrbPath;

//Evoke gain 1 strength
//Passive deal 4 damage to random enemy
public class MiyuAttackOrb extends AbstractOrb {

    // Standard ID/Description
    public static final String ORB_ID = DefaultMod.makeID("MiyuAttackOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;

    private static final Texture IMG = TextureLoader.getTexture(makeOrbPath("Common_orb.png"));
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;


    public MiyuAttackOrb() {

        ID = ORB_ID;
        name = orbString.NAME;
        img = IMG;

        evokeAmount = baseEvokeAmount = 1;
        passiveAmount = basePassiveAmount = 4;

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
            //this.evokeAmount = 2 + (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount-1);
            this.passiveAmount = 6 + (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount-1);
            //updateDescription();
        } else {
            //evokeAmount = baseEvokeAmount = 1;
            passiveAmount = basePassiveAmount = 4;
        }

    }

    @Override
    public void onEvoke() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, evokeAmount)));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA"));
    }

    @Override
    public void onEndOfTurn() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        DamageInfo info = new DamageInfo(m, passiveAmount);

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f));

        AbstractDungeon.actionManager.addToTop(new DamageAction(m, info));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(cX, cY)); // This is the purple-sparkles in the orb. You can change this to whatever fits your orb.
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(0.6f, 0.35f, 0.0f, c.a / 2.0f));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(0.9f, 0.7f, 0.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, -angle, 0, 0, 96, 96, false, false);
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
        return new MiyuAttackOrb();
    }
}

package PrismaMod.powers;

import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class MaidPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MaidPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Maid_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Maid_power32.png");

    public MaidPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + "3" + DESCRIPTIONS[1] + "3" + DESCRIPTIONS[2];
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if(damageType == DamageInfo.DamageType.NORMAL){
            return 2*damage;
        }
        return damage;
    }

    /*@Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount*2;
    }*/


    @Override
    public void atStartOfTurn() {
        //AbstractDungeon.player.gainEnergy(3);
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(3));
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(3));
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }


    @Override
    public AbstractPower makeCopy() {
        return new MaidPower(owner, source, amount);
    }
}

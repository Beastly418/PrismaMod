package PrismaMod.powers;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.Illya.IllyaAssassinPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class EvadePower extends AbstractInstallPower  {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("EvadePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public boolean IS_ATTACKED = false;
    public int NUM_EVADES;
    public int APPLY_POISON;
    public int GAIN_SHIVS;
    public boolean ASSASSIN_DAMAGE;
    public int NUM_ASSASSIN_ORBS;



    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Assassin_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Assassin_power32.png");

    public EvadePower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        //legacy
        this.install = install;

        NUM_EVADES = this.install.getNumEvades();
        APPLY_POISON = this.install.getNumPoisonPerAttack();
        GAIN_SHIVS = this.install.getNumShivs();
        ASSASSIN_DAMAGE = this.install.isAssassinDamage();
        NUM_ASSASSIN_ORBS = this.install.getNumAssassinOrbs();

        type = PowerType.BUFF;
        isTurnBased = true;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaAssassinPower(owner, source, amount, install);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        //AbstractDungeon.player.gainEnergy(3);
        //AbstractDungeon.actionManager.addToTop(new GainEnergyAction(3));
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return 0;
    }


}

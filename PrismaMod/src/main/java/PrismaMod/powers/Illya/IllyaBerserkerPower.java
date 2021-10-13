package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class IllyaBerserkerPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    //APPLY THESE TO ALL OF THE ENEMIES
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaBerserkerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public boolean DEAL_DOUBLE;
    public boolean TAKE_DOUBLE;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Berserker_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Berserker_power32.png");

    public IllyaBerserkerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        DEAL_DOUBLE = this.install.isDealDouble();
        TAKE_DOUBLE = this.install.isTakeDouble();

        type = PowerType.BUFF;
        isTurnBased = false;


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
        return new IllyaBerserkerPower(owner, source, amount, install);
    }

    /*@Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (TAKE_DOUBLE && type == DamageInfo.DamageType.NORMAL)
            return 2*damage;
        return damage;
    }*/

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (TAKE_DOUBLE && type == DamageInfo.DamageType.NORMAL)
            return 2*damage;
        return damage;
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (DEAL_DOUBLE && type == DamageInfo.DamageType.NORMAL)
            return 2*damage;
        return damage;
    }

    /*@Override
    public float atFinalDamageRecieve(float damage, DamageInfo.DamageType damageType) {
        if (DEAL_DOUBLE && damageType == DamageInfo.DamageType.NORMAL)
            return 2*damage;
        return damage;
    }*/

    /*
    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (TAKE_DOUBLE && info.type == DamageInfo.DamageType.NORMAL)
        return 2*damageAmount;
        return damageAmount;
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (DEAL_DOUBLE && info.type == DamageInfo.DamageType.NORMAL)
        return 2*damageAmount;
        return damageAmount;
    }
    */

}


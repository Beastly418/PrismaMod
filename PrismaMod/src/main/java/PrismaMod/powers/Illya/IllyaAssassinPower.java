package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.powers.EvadePower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;


public class IllyaAssassinPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaAssassinPower");
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

    public IllyaAssassinPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        NUM_EVADES = this.install.getNumEvades();
        APPLY_POISON = this.install.getNumPoisonPerAttack();
        GAIN_SHIVS = this.install.getNumShivs();
        ASSASSIN_DAMAGE = this.install.isAssassinDamage();
        NUM_ASSASSIN_ORBS = this.install.getNumAssassinOrbs();

        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("zhs")) {
            description = DESCRIPTIONS[0] + APPLY_POISON + DESCRIPTIONS[1] + NUM_ASSASSIN_ORBS + DESCRIPTIONS[2] + "75" + DESCRIPTIONS[3] + "75" + DESCRIPTIONS[4] + "1" + DESCRIPTIONS[5];
        } else {
            description = DESCRIPTIONS[0] + APPLY_POISON + DESCRIPTIONS[1] + NUM_ASSASSIN_ORBS + DESCRIPTIONS[2] + "75" + DESCRIPTIONS[3] + "75" + DESCRIPTIONS[4] + "3" + DESCRIPTIONS[5] + "1" + DESCRIPTIONS[6];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaAssassinPower(owner, source, amount, install);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.THORNS && type != DamageInfo.DamageType.HP_LOSS) {
            if ((int) (damage * .75) <= 1) {
                return 1;
            }
            return (int) (damage * .75);
        }
        return damage;
    }


    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType != DamageInfo.DamageType.THORNS && damageType != DamageInfo.DamageType.HP_LOSS)
            return (int) (damage*1.75f);
        return damage;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new PoisonPower(target, target, APPLY_POISON-1)));
        if(target != null && AbstractMonster.class.isAssignableFrom(target.getClass()) && info.type == DamageInfo.DamageType.NORMAL){
            AbstractMonster m = (AbstractMonster) target;
            if(m.type == BOSS){
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(owner, 3, DamageInfo.DamageType.HP_LOSS)));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new EvadePower(p, p, 1, this.install)));
    }

    /*@Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (!this.IS_ATTACKED) {
            this.IS_ATTACKED = true;
            return 0;
        }else {
            return (int) (damageAmount*1.25);
        }
    }*/

    @Override
    public void atStartOfTurn() {
        this.IS_ATTACKED = false;
        //AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Shiv(), GAIN_SHIVS));
    }
}

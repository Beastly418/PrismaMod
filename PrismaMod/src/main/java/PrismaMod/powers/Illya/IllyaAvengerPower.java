package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.powers.HalfDamagePower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IllyaAvengerPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaAvengerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;
    public int DPS;
    public boolean HALVE_NON_BOSS_DAMAGE;
    public int STRENGTH_INC;

    public AbstractCreature lastHit;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Avenger_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Avenger_power32.png");

    public IllyaAvengerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        DPS = this.install.getAoeDamagePerTurn();
        HALVE_NON_BOSS_DAMAGE = this.install.isHalfnonBossDamage();
        STRENGTH_INC = this.install.getStrengthInc();

        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + DPS + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaAvengerPower(owner, source, amount, install);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(source, new int[]{DPS,DPS,DPS,DPS}, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true));
        MonsterGroup monGroup = AbstractDungeon.getMonsters();
        for(AbstractMonster m : monGroup.monsters) {
            if(m.type == AbstractMonster.EnemyType.NORMAL) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new HalfDamagePower(m, m, 1, this.install)));
            }
        }
    }

    /*
    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(source, new int[]{DPS,DPS,DPS,DPS}, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true));
        MonsterGroup monGroup = AbstractDungeon.getMonsters();
        for(AbstractMonster m : monGroup.monsters) {
            if(m.type != AbstractMonster.EnemyType.BOSS) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new HalfDamagePower(m, m, 1, new IllyaInstallObj("Avenger"))));
            }
        }
    }*/
}

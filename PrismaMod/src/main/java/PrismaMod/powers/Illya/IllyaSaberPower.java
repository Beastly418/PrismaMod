package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.powers.SaberDamageIncreasePower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;

public class IllyaSaberPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    //APPLY THESE TO ALL OF THE ENEMIES
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaSaberPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public int STRENGTH_UP;
    public int BONUS_BOSS_DAMAGE;
    public int HEAL;
    public boolean hasAttacked;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Saber_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Saber_power32.png");

    public IllyaSaberPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        STRENGTH_UP = this.install.getStrengthInc();
        BONUS_BOSS_DAMAGE = this.install.getDealMoreDamageToBosses();
        HEAL = this.install.getHealPerTurn();

        this.hasAttacked = false;

        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("kor")){
            description = DESCRIPTIONS[0] + STRENGTH_UP + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + STRENGTH_UP + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaSaberPower(owner, source, amount, install);
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new StrengthPower(owner, STRENGTH_UP)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new SaberDamageIncreasePower(owner, owner, 1)));
        //AbstractDungeon.actionManager.addToTop(new HealAction(owner, owner, HEAL));
        hasAttacked = false;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target != null && AbstractMonster.class.isAssignableFrom(target.getClass()) && info.type == DamageInfo.DamageType.NORMAL){
            AbstractMonster m = (AbstractMonster) target;
            if(m.type == BOSS){
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(owner, BONUS_BOSS_DAMAGE, DamageInfo.DamageType.HP_LOSS)));
            }
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        /*boolean aoe = false;
        if(AbstractDynamicCard.class.isAssignableFrom(card.getClass())) {
            AbstractDynamicCard c = (AbstractDynamicCard) card;
            aoe = c.aoe;
        }
        if (!aoe) {
            if (card.type == AbstractCard.CardType.ATTACK && m.type == BOSS) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(owner, BONUS_BOSS_DAMAGE, DamageInfo.DamageType.HP_LOSS)));
            }
        } else if (aoe) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                MonsterGroup mon = AbstractDungeon.getMonsters();
                for (AbstractMonster monst : mon.monsters) {
                    if (monst.type == BOSS){
                        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(owner, BONUS_BOSS_DAMAGE, DamageInfo.DamageType.HP_LOSS)));
                    }
                }
            }
        }*/
    }

}


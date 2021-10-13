package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.NORMAL;

public class IllyaRiderPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    //APPLY THESE TO ALL OF THE ENEMIES
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaRiderPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public int DEX;
    public int DAMAGE_NON_BOSS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Rider_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Rider_power32.png");

    public IllyaRiderPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        DEX = this.install.getNumDex();
        DAMAGE_NON_BOSS = this.install.getMoreDamagetononBoss();

        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + DEX + DESCRIPTIONS[1] + DAMAGE_NON_BOSS + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaRiderPower(owner, source, amount, install);
    }


    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new DexterityPower(owner, DEX)));
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target != null && info.type == DamageInfo.DamageType.NORMAL){
            //AbstractMonster m = (AbstractMonster) target;
            MonsterGroup mon = AbstractDungeon.getMonsters();
            for(AbstractMonster monst : mon.monsters) {
                if(monst.type == NORMAL) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(monst, new DamageInfo(owner, DAMAGE_NON_BOSS, DamageInfo.DamageType.THORNS)));
                }
            }
        }
    }

    /*
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        boolean aoe = false;
        if(AbstractDynamicCard.class.isAssignableFrom(card.getClass())) {
            AbstractDynamicCard c = (AbstractDynamicCard) card;
            aoe = c.aoe;
        }
        if (!aoe) {
            if((card.type == AbstractCard.CardType.ATTACK) && (m.type != BOSS)){
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(owner, DAMAGE_NON_BOSS, DamageInfo.DamageType.HP_LOSS)));
            }
        } else if (aoe) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                MonsterGroup mon = AbstractDungeon.getMonsters();
                for (AbstractMonster monst : mon.monsters) {
                    if (monst.type != BOSS){
                        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(owner, DAMAGE_NON_BOSS, DamageInfo.DamageType.HP_LOSS)));
                    }
                }
            }
        }

    }*/

}


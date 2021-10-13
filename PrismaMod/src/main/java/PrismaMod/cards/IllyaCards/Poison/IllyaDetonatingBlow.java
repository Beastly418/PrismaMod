package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.actions.CardActions.Illya.Poison.DetonatingBlowAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaDetonatingBlow extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaDetonatingBlow.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/DetonatingBlow.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAM = 3;
    private static final int UPGRADED_COST = 1;



    // /STAT DECLARATION/

    public IllyaDetonatingBlow() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = damage = DAMAGE;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new DetonatingBlowAction(p, m, damage));
        } else {
            int numPoison;
            if (m.hasPower(PoisonPower.POWER_ID)) {
                AbstractPower pow = m.getPower(PoisonPower.POWER_ID);
                numPoison = pow.amount/4;
                //AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(m, numPoison, DamageInfo.DamageType.HP_LOSS)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
                //m.powers.remove(pow);

                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    try {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new PoisonPower(mon, mon, numPoison)));
                    } catch (Exception e) {
                        DefaultMod.logger.info("There aren't any monsters with poison why would you play this?");
                    }
                }
            }
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(m, damage, DamageInfo.DamageType.HP_LOSS)));//, AbstractGameAction.AttackEffect.NONE));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeDamage(UPGRADED_DAM);
            initializeDescription();
        }
    }

}

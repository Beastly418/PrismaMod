package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Orbs.ChanneledAttackAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//Deal damage based on number of orbs channeled hits random enemies
@Deprecated
public class IllyaChanneledAttack extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaChanneledAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/MultiEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int NUM_ATTACKS = 0;
    private static final int DAMAGE = 3;
    private static final int UP_DAMAGE = 1;

    public IllyaChanneledAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = NUM_ATTACKS;
        this.baseDamage = this.damage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new ChanneledAttackAction(p, this.damage));
        } else {
            for(int i = 0; i < this.magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeDamage(UP_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player != null)
            this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.orbs.size();
    }
}
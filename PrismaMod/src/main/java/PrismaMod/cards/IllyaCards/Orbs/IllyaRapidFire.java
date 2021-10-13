package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.RapidFireAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaRapidFire extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaRapidFire.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/RapidFire.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 3;


    public IllyaRapidFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
        this.baseDamage = this.damage = 4;//Illya.ORB_SLOTS;
        this.magicNumber = this.baseMagicNumber = Illya.ORB_SLOTS;
        //this.baseDamage = damage = baseMagicNumber = magicNumber = AbstractDungeon.player.orbs.size();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new RapidFireAction(p, m, upgraded, damage));
        } else {
            int numOrbs = p.maxOrbs;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs;
    }


    @Override
    public void triggerWhenDrawn() {
        this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs;
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            damage = damage*2;
            baseDamage = baseDamage*2;
            upgradedDamage = true;
            initializeDescription();
        }
    }


    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player != null)
            this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs;
    }
}

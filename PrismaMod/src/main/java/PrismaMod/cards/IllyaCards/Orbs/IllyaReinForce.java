package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.ReinForceAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaReinForce extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaReinForce.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/ReinForce.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 3;

    public IllyaReinForce() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;

        magicNumber = baseMagicNumber = Illya.ORB_SLOTS;
        block = baseBlock = 4;//Illya.ORB_SLOTS;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new ReinForceAction(p, upgraded, block));
        } else {
            int numOrbs = p.maxOrbs;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
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
            upgradedMagicNumber = true;
            block = block*2;
            baseBlock = baseBlock*2;
            upgradedBlock = true;
            initializeDescription();
        }
    }


    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null)
            this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs;
    }
}

package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.CheapDefendAction;
import PrismaMod.actions.CardActions.Illya.Normal.CommonAttackAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCheapDefend extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaCheapDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CheapDefend.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 0;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 3;

    // /STAT DECLARATION/

    public IllyaCheapDefend() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        this.baseBlock = this.block = BLOCK;

        //this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToBottom(new CheapDefendAction(p, this.block));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgraded = true;
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
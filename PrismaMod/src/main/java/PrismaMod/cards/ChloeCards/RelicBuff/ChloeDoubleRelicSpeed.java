package PrismaMod.cards.ChloeCards.RelicBuff;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.powers.Chloe.ChloeDoubleRelicSpeedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeDoubleRelicSpeed extends AbstractDynamicCard {
        public static final String ID = DefaultMod.makeID(ChloeDoubleRelicSpeed.class.getSimpleName());
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

        public static final String IMG = makeCardPath("Chloe/DoubleRelicSpeed.png");

        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;

        // /TEXT DECLARATION/


        // STAT DECLARATION

        private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
        private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
        private static final AbstractCard.CardType TYPE = CardType.POWER;
        public static final AbstractCard.CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

        private static final int COST = 3;
        private static final int UP_COST = 2;


        // /STAT DECLARATION/

    public ChloeDoubleRelicSpeed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);


    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ChloeDoubleRelicSpeedPower(p, p, 1)));
    }

        @Override
        public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeBaseCost(UP_COST);
        }
    }
}

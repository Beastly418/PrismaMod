package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.CarnivalOrbAction;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.*;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCarnivalOrbs extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaCarnivalOrbs.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CarnivalOrbs.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public IllyaCarnivalOrbs() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new CarnivalOrbAction(p));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAttackOrb(), true));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaDefendOrb(), true));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAssassinOrb(), true));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaTestamentOrb(), true));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaZweiOrb(), true));

        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}

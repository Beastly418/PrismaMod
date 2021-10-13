package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.UncommonEvokeAction;
import PrismaMod.characters.Illya;
import PrismaMod.patches.IllyaCardTags;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaUncommonEvoke extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaUncommonEvoke.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UncommonEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public IllyaUncommonEvoke() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(IllyaCardTags.EVOKE_CARD);
        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new UncommonEvokeAction(p));
        } else {
            AbstractOrb tmp = p.orbs.get(0);
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(tmp));
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

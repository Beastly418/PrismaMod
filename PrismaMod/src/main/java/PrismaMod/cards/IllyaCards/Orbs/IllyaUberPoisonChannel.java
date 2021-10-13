package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Orbs.UberChannelAction;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.IllyaAttackOrb;
import PrismaMod.orbs.IllyaPoisonOrb;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//channel all poison orbs
public class IllyaUberPoisonChannel extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaUberPoisonChannel.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UberPoisonChannel.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    public IllyaUberPoisonChannel() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            int numMaxOrbs = p.maxOrbs;
            for (int i = 0; i < numMaxOrbs; i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));

            }
        } else {
            int numMaxOrbs = p.maxOrbs;
            for (int i = 0; i < numMaxOrbs; i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));

            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
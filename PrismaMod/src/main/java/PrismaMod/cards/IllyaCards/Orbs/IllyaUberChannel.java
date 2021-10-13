package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.UberChannelAction;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.*;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaUberChannel extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaUberChannel.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UberChannel.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    public IllyaUberChannel() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new UberChannelAction(p));
        } else {
            AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());
            int numMaxOrbs = p.maxOrbs;
            int rand;
            for (int i = 0; i < numMaxOrbs; i++) {
                rand = (int) (Math.random() * 6);
                switch (rand) {
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAssassinOrb(), true));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAttackOrb(), true));
                        break;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaDefendOrb(), true));
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaPoisonOrb(), true));
                        break;
                    case 4:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaTestamentOrb(), true));
                        break;
                    case 5:
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaZweiOrb(), true));
                        break;
                }
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

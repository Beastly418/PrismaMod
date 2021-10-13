package PrismaMod.actions.CardActions.Chloe;

import PrismaMod.cards.ChloeCards.AbstractTraceCard;
import PrismaMod.cards.ChloeCards.TraceCards.ChloeCreateableTraceCard;
import PrismaMod.characters.Chloe;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.relics.PrismaModTraceOnRelic;
import PrismaMod.relics.PrismaModTraceOnUpgradeRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;


import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class TraceAction extends AbstractChloeAction {

    private static final Logger logger = LogManager.getLogger(TraceAction.class.getName());

    private AbstractCard.CardColor color;
    private AbstractCard.CardRarity rarity;
    private AbstractCard.CardType type;
    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private CardGroup temp;

    public String strings = languagePack.getCardStrings(ChloeCreateableTraceCard.ID).UPGRADE_DESCRIPTION; //<< SHOULD BE SOMETHING LIKE SELECT 1

    public TraceAction(AbstractPlayer p, ArrayList<AbstractCard> cards) {//AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardType type) {
        this.p = p;
        if (p instanceof Chloe) {
            //this.animHandler = getHandler(p);
            //this.animHandler.setAnimation(CHANNEL_ANIM);
        }
        /*
        this.color = color;
        this.rarity = rarity;
        this.type = type;*/
        this.isDone = false;

        this.cards = cards;//this.getCards();
        /*while (cards.size() < 1) {
            cards = this.getCards(AbstractCard.CardColor.RED);
        }*/
        temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard c : this.cards) {
            if(!(c instanceof AbstractTraceCard)) {
                UnlockTracker.markCardAsSeen(c.cardID);
                temp.group.add(c);
            }
        }
        gridSelectScreen.open(temp, 1, false, strings);
        //gridSelectScreen.confirmButton.hide();
        //gridSelectScreen.confirmButton.isDisabled = true;
    }

    @Override
    public void update() {

        if (!this.isDone && !gridSelectScreen.selectedCards.isEmpty()) {
            //gridSelectScreen.open(temp, 1, true, "Trace On");
            //AbstractDungeon.cardRewardScreen.open(cards, null, "TRACE ON");
            AbstractCard c = gridSelectScreen.selectedCards.get(0).makeStatEquivalentCopy();
            c.setCostForTurn(c.cost - 1 > 0 ? --c.cost : 0);
            actionManager.addToTop(new MakeTempCardInHandAction(c));
            if (this.p.hasRelic(PrismaModTraceOnRelic.ID)) {
                //((Chloe)this.p).chloeItemHolder.traceCards.add(c);
                ((PrismaModTraceOnRelic)(this.p.getRelic(PrismaModTraceOnRelic.ID))).traceCards.add(c);
                logger.info("Added" + c + "to the trace cards. There are now " + ((PrismaModTraceOnRelic)(this.p.getRelic(PrismaModTraceOnRelic.ID))).traceCards.size() + " cards available");
            } else if (this.p.hasRelic(PrismaModTraceOnUpgradeRelic.ID)) {
                //((Chloe)this.p).chloeItemHolder.traceCards.add(c);
                ((PrismaModTraceOnUpgradeRelic)(this.p.getRelic(PrismaModTraceOnUpgradeRelic.ID))).traceCards.add(c);
                logger.info("Added" + c + "to the trace cards. There are now " + ((PrismaModTraceOnUpgradeRelic)(this.p.getRelic(PrismaModTraceOnUpgradeRelic.ID))).traceCards.size() + " cards available");
            }
            gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }

        if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            this.isDone = true;
        }


        //logger.info(getRewardCards().get(0));


    }
}
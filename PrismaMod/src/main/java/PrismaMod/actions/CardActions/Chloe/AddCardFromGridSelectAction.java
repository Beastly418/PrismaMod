package PrismaMod.actions.CardActions.Chloe;

import PrismaMod.cards.ChloeCards.Misc.ChloeDemonicTutor;
import PrismaMod.cards.ChloeCards.Misc.ChloeReshuffleDiscard;
import PrismaMod.cards.ChloeCards.TraceCards.ChloeCreateableTraceCard;
import PrismaMod.cards.MiyuCards.Orbs.MiyuCheapEvoke;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.gridSelectScreen;

public class AddCardFromGridSelectAction extends AbstractChloeAction {

    private static final Logger logger = LogManager.getLogger(AddCardFromGridSelectAction.class.getName());

    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private CardGroup temp;
    private boolean discard;

    //public String strings = languagePack.getCardStrings(ChloeDemonicTutor.ID).UPGRADE_DESCRIPTION; //<< SHOULD BE SOMETHING LIKE SELECT 1
    public AddCardFromGridSelectAction(AbstractPlayer p, CardGroup cardGroup, int numCards, String note, boolean discard) {
        this(p, cardGroup.group, numCards, note, discard);
    }

    public AddCardFromGridSelectAction(AbstractPlayer p, CardGroup cardGroup, int numCards, String note) {
        this(p, cardGroup.group, numCards, note);
        this.temp = cardGroup;
    }

    public AddCardFromGridSelectAction(AbstractPlayer p, ArrayList<AbstractCard> cards, int numCards, String note, boolean discard){//AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardType type) {
        this.discard = discard;
        this.p = p;
        if (p instanceof Chloe){
            //this.animHandler = getHandler(p);
            //this.animHandler.setAnimation(CHANNEL_ANIM);
        }
        this.isDone = false;
        this.cards = cards;//this.getCards();

        if(cards.size() < 1 && !this.discard) {
            if(p instanceof Chloe) {
                cards.add(new ChloeCreateableTraceCard());
            } else if(p instanceof Miyu) {
                cards.add(new MiyuCheapEvoke());
            } else {
                AbstractCard tmpCd = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON);
                cards.add(tmpCd);
            }
        }

        temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for(AbstractCard c : cards) {
            UnlockTracker.markCardAsSeen(c.cardID);
            temp.group.add(c);
        }
        gridSelectScreen.open(temp, numCards, true, note);
        //gridSelectScreen.confirmButton.hide();
        //gridSelectScreen.confirmButton.isDisabled = true;
    }

    //Old base constructor going to still keep
    public AddCardFromGridSelectAction(AbstractPlayer p, ArrayList<AbstractCard> cards, int numCards, String note){//AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardType type) {
        this.p = p;
        if (p instanceof Chloe){
            //this.animHandler = getHandler(p);
            //this.animHandler.setAnimation(CHANNEL_ANIM);
        }
        this.isDone = false;
        this.cards = cards;//this.getCards();

        if(cards.size() < 1 && !this.discard) {
            if(p instanceof Chloe) {
                cards.add(new ChloeCreateableTraceCard());
            } else if(p instanceof Miyu) {
                cards.add(new MiyuCheapEvoke());
            } else {
                AbstractCard tmpCd = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON);
                cards.add(tmpCd);
            }
        }

        temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for(AbstractCard c : cards) {
            UnlockTracker.markCardAsSeen(c.cardID);
            temp.group.add(c);
        }
        gridSelectScreen.open(temp, numCards, true, note);
        //gridSelectScreen.confirmButton.hide();
        //gridSelectScreen.confirmButton.isDisabled = true;
    }
    @Override
    public void update() {
        if(!this.isDone && !gridSelectScreen.selectedCards.isEmpty()){
            for(AbstractCard c : gridSelectScreen.selectedCards) {
                this.p.hand.moveToHand(c, this.temp);
                //this.p.hand.addToHand(c);
                this.temp.group.remove(c);
            }
            gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.isDone = true;
        }

        if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            this.isDone = true;
        }
        //logger.info(getRewardCards().get(0));
    }
}
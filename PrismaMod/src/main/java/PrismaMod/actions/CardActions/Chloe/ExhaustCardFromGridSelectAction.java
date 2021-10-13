package PrismaMod.actions.CardActions.Chloe;

import PrismaMod.cards.ChloeCards.Normal.ChloeShiv;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class ExhaustCardFromGridSelectAction extends AbstractChloeAction {

    //THIS ONLY WORKS FOR A HAND!!!
    //This action will exhaust X amount of cards given to it and then give the player a specified number of 0 cost Mill cards to burn through

    private int numShivs;
    private ArrayList<AbstractCard> cards;
    private CardGroup temp;

    public ExhaustCardFromGridSelectAction(AbstractPlayer p, CardGroup cards, int numShivs, String msg, int amt) {
        this(p, cards.group, numShivs, msg, amt);
        this.temp = cards;
    }

    public ExhaustCardFromGridSelectAction(AbstractPlayer p, CardGroup cards, int numShivs, String msg, int amt, AbstractCard card) {
        this(p, cards.group.remove(card) ? cards : cards, numShivs, msg, amt);
        this.temp = cards;
    }

    public ExhaustCardFromGridSelectAction(AbstractPlayer p, ArrayList<AbstractCard> cards, int numShivs, String msg, int amt, AbstractCard card) {
        this(p, cards.remove(card) ? cards : cards, numShivs, msg, amt);
    }

    public ExhaustCardFromGridSelectAction(AbstractPlayer p, ArrayList<AbstractCard> cards, int numShivs, String msg, int amt){

        this.cards = cards;
        this.numShivs = numShivs;
        this.p = p;
        this.isDone = false;

        this.temp = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for(AbstractCard c : cards) {
            UnlockTracker.markCardAsSeen(c.cardID);
            this.temp.group.add(c);
        }
        AbstractDungeon.gridSelectScreen.open(this.temp, amt, true, msg);
    }

    @Override
    public void update() {
        if(!this.isDone && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, this.temp));
                for(int i = 0; i < this.numShivs; i++)
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new ChloeShiv()));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.isDone = true;
        }
        if(!this.isDone && this.temp.group.size() == 0){
            this.p.hand.refreshHandLayout();
            this.isDone = true;
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            this.isDone = true;
        }

    }
}

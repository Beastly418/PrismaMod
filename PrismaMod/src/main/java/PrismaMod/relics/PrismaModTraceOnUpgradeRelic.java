package PrismaMod.relics;

import PrismaMod.DefaultMod;
import PrismaMod.cards.ChloeCards.ChloeCarnivalCard;
import PrismaMod.cards.ChloeCards.TraceCards.ChloeCreateableTraceCard;
import PrismaMod.patches.InstallColorEnum;
import PrismaMod.powers.Chloe.ChloeBetterTracePoolsPower;
import PrismaMod.powers.Chloe.ChloeDoubleRelicSpeedPower;
import PrismaMod.powers.Chloe.ChloeGreatTracePoolsPower;
import PrismaMod.util.TextureLoader;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Map;

import static PrismaMod.DefaultMod.*;

public class PrismaModTraceOnUpgradeRelic extends CustomRelic implements CustomSavable<String> {

    public static final String ID = DefaultMod.makeID("PrismaTraceOnUpgrade");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Chloe_Relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    //private int counter;
    private int traceOn = 20;
    private static ArrayList<AbstractCard.CardColor> cardColors;

    private AbstractCard.CardColor colorSelect;
    private AbstractCard.CardType typeSelect;
    private AbstractCard.CardRarity raritySelect;

    private ArrayList<AbstractCard> cards = new ArrayList<>(20);
    private PrismaModTraceOnRelic.TraceObject group;

    public ArrayList<AbstractCard> traceCards = new ArrayList<>(30);

    //public static ArrayList<AbstractCard> traceCards = new ArrayList<>(30);

    public PrismaModTraceOnUpgradeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        this.counter = 0;
        this.cardColors = new ArrayList<>(20);
        AbstractCard.CardColor[] enums = AbstractCard.CardColor.class.getEnumConstants();
        for(AbstractCard.CardColor color : enums){
            if(color != InstallColorEnum.COLOR_INSTALL && color != AbstractCard.CardColor.COLORLESS && color != AbstractCard.CardColor.CURSE ) {
                this.cardColors.add(color);
            }
        }
        try {
            if (AbstractDungeon.player.hasRelic(PrismaModTraceOnRelic.ID)) {
                this.traceCards = ((PrismaModTraceOnRelic) AbstractDungeon.player.getRelic(PrismaModTraceOnRelic.ID)).traceCards;
                //AbstractDungeon.player.relics.remove(AbstractDungeon.player.getRelic(PrismaModTraceOnRelic.ID));
            }
        } catch (NullPointerException e) {

        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        int n = 0;
        //this.flash();
        //AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, DRAW_AMOUNT));
        this.counter++;
        if(AbstractDungeon.player.hasPower(ChloeDoubleRelicSpeedPower.POWER_ID)) {
            this.counter++;
        }
        if(c instanceof ChloeCarnivalCard){
            this.counter++;
        }
        if (this.counter >= this.traceOn) {
            this.cards.clear();
            while(cards.size() < 3) { //Make sure you always get at least 4 cards to choose from
                this.roll();
                this.cards = getCards(this.group);
                n++;
                if (n > 35) {   //If we don't get a usable pull in 35 times then something weird happened and go to the default
                    this.cards = getCards(new PrismaModTraceOnRelic.TraceObject(AbstractCard.CardColor.RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.ATTACK));
                    //logger.info("");
                    break;
                }
            }
            AbstractCard tmp = new ChloeCreateableTraceCard(this.colorSelect, this.raritySelect, this.typeSelect, this.cards, true);
            tmp.upgrade();
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(tmp.makeStatEquivalentCopy()));
            this.counter = 0;
        }
    }

    public static ArrayList<AbstractCard> getCards(PrismaModTraceOnRelic.TraceObject group){
        ArrayList<AbstractCard> cards = new ArrayList<>(20);
        AbstractCard.CardRarity rarity = group.cardRarity;
        AbstractCard.CardType type = group.cardType;
        AbstractCard.CardColor color = group.cardColor;
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            AbstractCard card = c.getValue();
            if(card.type == type && card.color == color && card.rarity == rarity) {
                card.upgrade();
                cards.add(card.makeStatEquivalentCopy());
                //logger.info("pulled " + card);
            }
        }
        return cards;
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(PrismaModTraceOnRelic.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(PrismaModTraceOnRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        super.updateDescription(c);
    }

    public void roll(){
        int randColor = MathUtils.random(0, cardColors.size() - 1);
        int randType = MathUtils.random(0, 2);
        int randRarity = MathUtils.random(0, 2);

        //Get all of the parts of the card
        this.colorSelect = cardColors.get(randColor);
        if(AbstractDungeon.player.hasPower(ChloeGreatTracePoolsPower.POWER_ID)) {
            this.raritySelect = AbstractCard.CardRarity.RARE;
        } else if(AbstractDungeon.player.hasPower(ChloeBetterTracePoolsPower.POWER_ID)) {

            if(randRarity <= 1){
                this.raritySelect = AbstractCard.CardRarity.UNCOMMON;
            } else {
                this.raritySelect = AbstractCard.CardRarity.RARE;
            }
        } else {
            switch(randRarity){
                case 0:
                    this.raritySelect = AbstractCard.CardRarity.COMMON;
                    break;
                case 1:
                    this.raritySelect = AbstractCard.CardRarity.UNCOMMON;
                    break;
                case 2:
                    this.raritySelect = AbstractCard.CardRarity.RARE;
                    break;
            }
        }
        switch(randType){
            case 0:
                this.typeSelect = AbstractCard.CardType.SKILL;
                break;
            case 1:
                this.typeSelect = AbstractCard.CardType.POWER;
                break;
            case 2:
                this.typeSelect = AbstractCard.CardType.ATTACK;
                break;
        }
        this.group = new PrismaModTraceOnRelic.TraceObject(this.colorSelect, this.raritySelect, this.typeSelect);
        logger.info(this.group);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(PrismaModTraceOnRelic.ID);// 36
    }





    @Override
    public String onSave() {

        registerSaveId();

        logger.info(getLogPrefix("onSave") + " called");

        String holdingCell = "";
        for(AbstractCard c : this.traceCards) {
            holdingCell = holdingCell + c.cardID + "@";
            logger.info(holdingCell);
        }
        return holdingCell;
    }

    @Override
    public void onLoad(String s) {
        if(s != null) {
            logger.debug(getLogPrefix("onLoad") + " Loading cards for card pool from save:");
            String[] ids = s.split("@");
            int cardIndex = 0;
            for(String id : ids) {
                if(CardLibrary.isACard(id)) {
                    AbstractCard card = CardLibrary.getCard(id);
                    this.traceCards.add(card);
                    logger.debug(cardIndex++ + ") " + card.name + " [cardId: " + card.cardID + "]");
                } else {
                    logger.warn(cardIndex++ + ") NOT IN CARD LIBRARY [cardId: " + id + "]");
                }
            }
        }
    }


    private void registerSaveId() {
        logger.debug( this.getClass().getSimpleName() + "::registerSaveId");
        logger.debug( this.getClass().getSimpleName() + "::registerSaveId registering customSaveKey: " + getCustomSaveKey());
        BaseMod.addSaveField(this.getCustomSaveKey(), this);
    }

    public String getCustomSaveKey(){
        return DefaultMod.getModID() + this.getClass().getSimpleName();
    }

    private String getLogPrefix(String methodName) {
        return this.getClass().getSimpleName() + "." + methodName + " ::";
    }
}

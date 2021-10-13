package PrismaMod.cards.MiyuCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuDefenseExplosion extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuDefenseExplosion.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/DefenseExplosion.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 1;
    private static final int UP_COST = 0;


    // /STAT DECLARATION/

    public MiyuDefenseExplosion() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);



    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mon : AbstractDungeon.getMonsters().monsters){
            if(mon.currentBlock > 0){
                int i = mon.currentBlock;
                AbstractDungeon.actionManager.addToTop(new LoseBlockAction(mon, p, mon.currentBlock));
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, i, DamageInfo.DamageType.NORMAL)));
            }
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeBaseCost(UP_COST);
        }
    }
}

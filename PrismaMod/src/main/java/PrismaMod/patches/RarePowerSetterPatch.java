package PrismaMod.patches;

import PrismaMod.cards.IllyaCards.IllyaInstallCards.AbstractInstallCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static PrismaMod.DefaultMod.*;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderPowerPortrait"
)
public class RarePowerSetterPatch {
    private static final Logger logger = LogManager.getLogger(DefaultInsertPatch.class.getName());

    public static final String OriginalPowerString = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup").TEXT[2];

    public static TextureAtlas cardUiAtlas = new TextureAtlas(Gdx.files.internal("cardui/cardui.atlas"));
    public static TextureAtlas.AtlasRegion originalFrame = cardUiAtlas.findRegion("512/frame_power_rare");
    public static TextureAtlas.AtlasRegion originalFrame_L = cardUiAtlas.findRegion("1024/frame_power_rare");

    public static TextureAtlas.AtlasRegion blank = new TextureAtlas.AtlasRegion(new Texture(INSTALL_BLANK), 0, 0, 1, 1);

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {}
    )
    public static void patchMethod(AbstractCard __instance,SpriteBatch sb, float x, float y){
        //logger.info("Hey RarePowerPatched");
        //String tempText = __instance.TEXT[2];
        if (__instance instanceof AbstractInstallCard ) {
            //logger.info("Install Card");
            ImageMaster.CARD_FRAME_POWER_RARE_L = blank;
            ImageMaster.CARD_FRAME_POWER_RARE = blank;
            __instance.TEXT[2] = "";
        } else {
            //logger.info("Not Install");
            ImageMaster.CARD_FRAME_POWER_RARE = originalFrame;
            ImageMaster.CARD_FRAME_POWER_RARE_L = originalFrame_L;
            __instance.TEXT[2] = OriginalPowerString;
        }
    }

    private static class Locator extends SpireInsertLocator { // Hey welcome to our SpireInsertLocator class!
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {// All the locator has and needs is an override of the Locate method

            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderHelper");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }

    }
}


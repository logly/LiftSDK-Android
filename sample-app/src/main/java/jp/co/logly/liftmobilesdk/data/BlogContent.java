package jp.co.logly.liftmobilesdk.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class BlogContent {

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, BlogItem> ITEM_MAP = new HashMap<String, BlogItem>() {{
        put("1", new BlogItem("1",
                "http://blog.logly.co.jp/20160401/000015",
                "NYタイムズがソーシャル・インフルエンサーを活用、ブランドコンテンツのクリエイティブのためにマーケティング会社を買収",
                "NYタイムズは今月上旬から、外部のメディアサイトのネイティブ広告を利用し始めています。同社は年初からネイティブ広告の提供を開始していたので、ネイティブ広告メディアとして今後の展開に注目していました。そのNYタイムズが立場を変えて、「広告主」として他サイトのネイティブ広告にコンテンツを出稿したのです。そこで、どのようなコンテンツを配信しているかを見ていきましょう。　NYタイムズが利用したネイティブ広告提供メディアは「Mashable」です。Mashableはインターネット分野をカバーしたオンライン・ニュー"));
        put("2", new BlogItem("2",
                "http://blog.logly.co.jp/20151225/000014",
                "世界のネイティブ広告費、今後3年間で倍増し約600億ドルに、日本市場も同じく倍増",
                "世界各国のネイティブ広告費はどれくらいの規模で、今後どれくらい成長するのでしょうか。大雑把でいいから知りたいなと思っていると、つい最近、ネイティブ広告プラットフォームの大手事業者Adyoulikeが、世界の主要国でのネイティブ広告費を予測していました。それによると、今年のネイティブ広告費は世界全体で309億ドルで、2018年には593.5億ドルに達すると予測しています。3年間でほぼ倍増ですから、すごい急成長ですね。身内のネイティブ広告プレイヤーの予測なので、少し割り引いて見たほうが良さそうですが、伸びるのは間違いないでしょう。ただ、ネイティブ広告の市場と言っても、どれがネイティブ広告であるかという、共通の線引きが固まっていないのが現状です。米国の業界団体IABもネイティブ広告の範囲を定義していますが、そこでは検索連動広告もネイティブ広告に含むとしています。でも広告市場調査では、一般に検索広告をネイティブ広告から外しています。このため、ネイティブ広告の市場規模があいまいなままになっていました。それでもこれまで、比較的よく引用されていたのがBusiness Insiderが出していた次の図（図1）のグラフです。昨年の半ばあたりにから販売している有料資料の中で使っているグラフで、資料の販促のために何度も繰り返し、このグラフを載せた記事をBusiness Insiderが発信していたので、ご覧になった方もおられるでしょう。"));
        put("3", new BlogItem("3",
                "http://blog.logly.co.jp/20150612/000013",
                "CNNが広告向けコンテンツスタジオを立ち上げ、ニュース価値のあるスポンサード動画を制作",
                "メディアサイトでは、編集コンテンツだけではなくて広告でも、動画がやたらに増えてきました。フェイスブックのニュースフィードでも最近、動画コンテンツと頻繁に出会うようになってきました。ソーシャル系サイトでも、SnapchatやVineのような動画ベースのSNSが、若い人を中心に人気急上昇です。動画広告の市場も図1で示すように急伸しています。eMarketerの調査によりますと、2014年の動画広告費は米国で59億ドルと、前年比でプラス56%も急上昇しています。今年も前年比30.4%増の高成長を続け、78億ドル近くに達すると予測されています。ネイティブ広告のコンテンツでも、動画の採用が一段と盛んになっています。"));
    }};

    /**
     * An array of sample (dummy) items.
     */
    public static final List<BlogItem> ITEMS = new ArrayList<BlogItem>(ITEM_MAP.values());

    /**
     * A dummy item representing a piece of content.
     */
    public static class BlogItem {
        public final String id;
        public final String url;
        public final String title;
        public final String details;

        public BlogItem(String id, String url, String title, String details) {
            this.id = id;
            this.url = url;
            this.title = title;
            this.details = details;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

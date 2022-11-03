package com.ecjtu.onlinemusic;

import com.ecjtu.onlinemusic.dao.ActivityDao;
import com.ecjtu.onlinemusic.dao.CommentDao;
import com.ecjtu.onlinemusic.dao.ConsumerDao;
import com.ecjtu.onlinemusic.dao.MusicDao;
import com.ecjtu.onlinemusic.entity.Activity;
import com.ecjtu.onlinemusic.entity.Comment;
import com.ecjtu.onlinemusic.entity.Consumer;
import com.ecjtu.onlinemusic.entity.Music;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @description: 更新url
 * @author: 何江涛
 * @create: 2022-06-25
 * @version: 1.0
 */
@SpringBootTest
public class UpdateUrl {

    @Autowired
    ConsumerDao consumerDao;
    @Autowired
    MusicDao musicDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    CommentDao commentDao;

    // 用户
    @Test
    void updateConsumer() {
        Consumer consumer = new Consumer();
        int index = 0;
        for (int i = 1; i <= 130; i++) {
            consumer.setId(i);
            index = i % 10;
            // 用户头像
            consumer.setPictureUrl("http://localhost:8089/files/source/" + index + ".jpg");
            consumerDao.update(consumer);
        }

    }

    // 音乐
    @Test
    void updateMusic() {
        Music music = new Music();
        int index = 0;
        for (int i = 1; i <= 300; i++) {
            music.setId(i);
            index = i % 10;
            // 音乐图片
            music.setPictureUrl("http://localhost:8089/files/source/" + index + ".png");
            // 音乐url
            music.setUrl("http://localhost:8089/files/source/" + index + ".mp3");
            musicDao.update(music);
        }
    }

    // 活动
    @Test
    void updateActivity() {
        Activity activity = new Activity();
        int index = 0;
        for (int i = 1; i <= 100; i++) {
            activity.setId(i);

            // 活动图片
            index = i % 8;
            activity.setPictureUrl("http://localhost:8089/files/source/10" + index + ".jpg");

            // 活动内容
            index = i % 6;
            activity.setContent(getActivity(index));
            activityDao.update(activity);
        }
    }

    // 评论内容
    @Test
    void updateCommentContent() {
        Comment comment = new Comment();
        int index = 0;

        for (int i = 1; i <= 1980; i++) {
            comment.setId(i);
            index = i % 17;
            // 评论内容
            comment.setContent(getContent(index));
            commentDao.update(comment);
        }
    }



    static String getContent(Integer index) {
        String[] contents = new String[]{
                "我的初中生活终于结束了，当初想着早点离开，现在却不想离开了，舍不得同学，u1s1祝你们都前程似锦叭！",
                "别人谈恋爱被绿一次就不相信爱情了，我是越挫越勇，再来十个渣男。我倒要看看还有什么套路和手段是我不知道",
                "你说的再见 是哪个再见 再也不见 还是再次相见",
                "我发小，缉毒警，前些年谈的女朋友第二个月他就接到任务了，到今天六年多了没有半点我发小的消息，他曾说如果你有一天遇见了一块无名碑，可能就是他",
                "这首歌背后还有个真实故事，作词人唐恬，1983年出生于湖南，中国内地作词人。",
                "爱你又菜又爱装，" +
                        "爱你漏刀的模样，\n" +
                        "爱你对线被打穿，不肯出肉装。" +
                        "去吗？去啊！以最垃的出装！\n" +
                        "团吗！团啊！以五换零收场！" +
                        "致你上单时的无能与狂怒，\n" +
                        "谁说0-20的不配叫做英雄。",
                "中奖了兄弟们 肺癌 人生这趟车我就先下了 你们接着奏乐接着舞 哈哈其实想想也没什么 不是么...",
                "本来还觉得苦茶子怪有内种高级感，当我读出来终于明白为什么那么多人说发在朋友圈丢人了",
                "我有预感，点赞这条评论的人，痘痘会慢慢消下去，皮肤会变白变瘦，世人如满天星斗，而你皆如万人捧花",
                "我朋友告诉我这首歌叫《放了个响屁》我找了半天",
                "我喜欢有纹身的男孩、我喜欢拽拽的男孩、喜欢超级阳光的大男孩、我喜欢……遇见你后所有的标准都变了，脑海都是你，但有太多的不确定，不敢说爱你。 ",
                "月老 麻烦下次为我牵红线的时候能换成钢丝吗 红线老他妈断",
                "亲爱的周游 我要往前走了 我从未真心埋怨过你 你永远在我记忆里温温柔柔 我会在每个有意义的时刻祝你快乐",
                "一首属于夏天的歌，有海风有阳光还有你。",
                "这个夏天对我真的很重要！好喜欢这首歌！一定要保佑我考上我想考的学校啊！！！",
                "正在拉粑粑，夏天拉粑粑实在太热了",
                "庆幸熬过那段时光，才能有今天主页里满满的作品和视频，而不是灰色的头像和再也找不见的踪影。",
        };
        // 0 - 16
        return contents[index];
    }

    static String getActivity(Integer index) {
        String[] activitues = new String[]{
                "藏品灵感来自千禧年代风靡一时的QQ秀，这是我们这代人成长当中的共同记忆，我根据生命能量五大元素制作了不同的形象,并为每个形象定制了专属音乐。除了乐观及盲目浪漫的Y2K美学之外其社交属性，以及头像文化都和当下的数字藏品语境有着很多对应的关系。",
                "王者荣耀，推出新国乐音乐厂牌「听见·国乐」听见国乐之美，听见更多英雄。“听见国乐”，听见国乐的更多可能性。将游戏与国风音乐巧妙结合，在保留民乐艺术性的基础上，融入流行元素，酝酿、发酵，为国乐注入流行性，让国乐迸发新鲜活力。于传统与现代的激情碰撞中，感受豪气峡谷，解读王者英雄。「听见·国乐」首支单曲《婉》发。",
                "「百川回岸浪花永向前」“船归港，浪归岸”，在香港回归25周年之际，由人民日报新媒体x哗哩哗哩《仲夏港乐纪》联合出品，中凰Music Studio承担音乐制作、酷狗星曜协力发行，歌手陈楚生参与献声演唱的仲夏港乐纪主题曲《港湾》柔情释出。在香港回归25周年之际，祝福香港，用歌声祝福两岸伟大的祖国母亲永远繁荣昌盛!",

                "云村地图建设快要结束了？！这可能是今年在云村拥有房子的最后机会了， 快抓紧最后的时间搭建你的豪宅和花园！ 只要参与，为云村建设添砖加瓦，云村建成后就能和其他村民一起拿会员福利！",
                "测测你的乐迷等级，99%的人只有3级！打卡话题解锁乐迷等级，在活动任意话题内成功发布合格动态，达到7级可领取【超级乐迷】绝版头像框；达到10级可领取【云村周边礼包】！",
                "在过完“出道3000天纪念日”不久后，\n" +
                        "GOT7再次出道了。韩国男子组合GOT7于2014年出道，由林在范（JAYB) 、朴珍荣(JinYoung)、段宜恩(Mark)、王嘉尔(Jackson) 、崔荣宰(YoungJae) 、 BamBam、金有谦（YuGyeom)7人组成。\n" +
                        "2021年,7人集体与原经纪公司全员到期不续约。\n" +
                        "在歌迷与路人都在惋惜团队的走散时，他们集体牵手华纳，并于2022年5月，以团体身份发行了新专辑《GOT7》。「是迄今为止最像GOT7风格的专辑」一起走进“新人”男团GOT7新专辑《GOT7》的幕后故事吧!",



        };
        // 0 - 5
        return activitues[index];
    }

}

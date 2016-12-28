package com.yuanpin.flora.test.web.recommend;


import com.yuanpin.flora.test.web.base.BaseTest;
import org.testng.annotations.Test;

/**
 * Created by Minutch on 15/7/9.
 */
public class MainPageSectionControllerTest extends BaseTest{
    private static final String module = "mainPageSection/";

    @Test
    public void testMainPage() {

//        System.out.println("------------------------------开始测试首页活动接口列表（testMainPage）-------------------------------");
//
//        String response = HttpUtils.sendGet(host+module, "");
//        Gson gson = new Gson();
//        Result<Map<String, List<MainPageSectionVO>>> result = gson.fromJson(response, new TypeToken<Result<Map<String,List<MainPageSectionVO>>>>(){}.getType());
//        //判断接口返回结果是否符合预期
//        Assert.assertEquals(result.isSuccess(),true);
//
//        //推荐列表
//        List<MainPageSectionVO> mainPageSectionVOList = result.getData().get("category");
//        System.out.println("首页推荐模块数量:" + mainPageSectionVOList.size());
//        Assert.assertEquals(mainPageSectionVOList.size()>0,true);
//        for (MainPageSectionVO mainPageSectionVO : mainPageSectionVOList) {
//            //打印模块名称
//            System.out.println("\t模块名称："+mainPageSectionVO.getSectionTitle());
//            //校验模块内容
//            Assert.assertNotNull(mainPageSectionVO.getPageId());
//            Assert.assertNotNull(mainPageSectionVO.getPageParam());
//            Assert.assertNotNull(mainPageSectionVO.getLayoutId());
//            Assert.assertNotNull(mainPageSectionVO.getLayoutParam());
//            //校验模块图片
//            for (MainPageSectionItemVO mainPageSectionItemVO:mainPageSectionVO.getItems()) {
//                Assert.assertEquals(mainPageSectionItemVO.getItemImg().startsWith("http://"),true);
//            }
//        }
//        //Banner图检查
//        List<MainPageSectionVO> bannerPageSectionVOList = result.getData().get("banner");
//        System.out.println("首页Banner模块数量:" + bannerPageSectionVOList.size());
//        Assert.assertEquals(bannerPageSectionVOList.size()>0,true);
//        for (MainPageSectionVO bannerPageSectionVO : bannerPageSectionVOList) {
//            //打印模块名称
//            System.out.println("\t模块名称："+bannerPageSectionVO.getSectionTitle());
//            //校验模块内容
//            Assert.assertNotNull(bannerPageSectionVO.getPageId());
//            Assert.assertNotNull(bannerPageSectionVO.getPageParam());
//            Assert.assertNotNull(bannerPageSectionVO.getLayoutId());
//            Assert.assertNotNull(bannerPageSectionVO.getLayoutParam());
//            //校验模块图片
//            for (MainPageSectionItemVO mainPageSectionItemVO:bannerPageSectionVO.getItems()) {
//                Assert.assertEquals(mainPageSectionItemVO.getItemImg().startsWith("http://"),true);
//            }
//        }
//
//        System.out.println("------------------------------结束测试首页活动接口列表（testMainPage）-------------------------------");
    }


}

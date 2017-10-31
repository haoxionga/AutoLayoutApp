###感谢:张鸿洋大神的《Android AutoLayout全新的适配方式 堪称适配终结者》

[http://blog.csdn.net/lmj623565791/article/details/49990941](http://blog.csdn.net/lmj623565791/article/details/49990941)

###实现原理：
		设置基准宽高的px,dp值，获取当前设备的宽高的px，dp值，
		例如 x轴的值，double newvalue =(当前宽度/基准宽度)*具体的值，如此就可以得到适合屏幕比例的值，与设计稿比例保持一致

		android6.0以上设备使用反射的方法，将View的mDebugViewAttributes字段置为true，它在初始化的时候就会去保存属性集合，以键值对的形式
		android6.0以下，5.0设备获取到的属性集合不全，4.4及以下获取不到，所以采用传入布局文件id的形式，解析布局文件，生成对应的ViewBean，解析完布局文件后，遍历contentView，将对应的ViewBean的view地址指向遍历中对应view

###特点
	
	1.根据dimens的属性名，给予不同的比例，比如设置正方形的view的时候，宽高都以x_开头，就会都以x轴的比例进行计算，如果想view的宽高保持同一比例，最好就以x_，y_,t_开头，这样计算宽高的时候就会以x轴的比例进行计算

	2.设计灵活，对于不需要适配的属性，只要它的dimens命名不以x_开头或者在布局文件中直接写入具体值，就不会重新计算且设置尺寸

	3.如果原有计算比例规则不满足需求，可以自定义尺寸计算规则，在Application初始化的时候传入新的计算接口

	4.支持dp和px单位

	5.支持改变单个界面的尺寸单位，最大限度适配，在AutoLayout注解中填入参数，
	
	
###效果图来一发
		
	
 #宽度480px，高度800px，屏幕为hdpi  模拟器,宽度1080px，
		
<img src="https://github.com/zhouqihao/AutoLayoutApp/blob/master/screen/device-480-800-hdpi.png" width = "240" height = "400" style="margin-top:30px"  float="left"/>

 #高度1920px，屏幕为420dpi 模拟器

 <img src="https://github.com/zhouqihao/AutoLayoutApp/blob/master/screen/device-1080-1920-420dpi.png" width = "240" height = "427" style="margin-top:30px"   float="left"/>

 #宽度1080px，高度1920px，屏幕为360dpi,华为

 <img src="https://github.com/zhouqihao/AutoLayoutApp/blob/master/screen/huawei-1080-1920-360dpi.jpg" width = "240" height = "320" style="margin-top:30px"  float="left" />
 #小米平板
 <img src="https://github.com/zhouqihao/AutoLayoutApp/blob/master/screen/%E5%B0%8F%E7%B1%B3pad.jpg" width = "240" height = "320" style="margin-top:30px"  float="left" />
		




####准备工作

	1：在gradle中添加依赖：compile 'com.hx.autolayout:autolayout:1.0.7' ，（或者直接clone源码，导入library）
	2：在dimens中生成尺寸
		
		x轴的尺寸以：x_ 开头
			：x_10px,x_20px
		y轴的尺寸以：y_ 开头

		字号的尺寸以：t_ 开头

	解析的时候会根据不同的属性名开头，给予不同的比例

		项目中app,test包下dimenUtil可以批量生成

	3：类介绍：

	    常用：
        LayoutSizeUtil:初始化的类，以及设置view的尺寸的方法
	    IterationContentViewUtil：遍历工具类，遍历viewgroup，调用LaoutSizeUtil设置尺寸的方法
	    ReckonSizeListener :自定义的计算尺寸的接口，需在application中初始化传入
	    AutoLayout 注解，在onCreate或者类上使用，不用也可以，默认适配屏幕
	         参数：
	            isAutoLayout：是否适配，false的情况当前页面不会重新设置尺寸
	            isChangeSizeType：是否改变当前页面默认尺寸，true的情况使用Application中定义的宽度，高度，字体尺寸，false的情况需要传入widthUnit，height...
	            widthUnit：宽度尺寸，px还是dp，还是sp
###使用


1：在自定义Application中初始化
		
		 

  
		方式一:

			 /**
		     * @param sizeUnitBean      尺寸单位bean，为空的情况，默认定义单位为dp
		     * @param baseWith          基准宽度，px尺寸
		     * @param baseContentHeight 基准内容高度px尺寸，内容宽度 不包括状态栏！！！！
		     * @param baseWidthDp       基准宽度dp ,dp尺寸
		     * @param baseContentHeightDp      基准内容高度dp，dp尺寸
		     * @param application       上下文
		     */
	
		  LayoutSizeUtil.getInstance().initConfig(1080,1920,480, 800, SizeUnitBean.getDefaultPx(), this);

		方式二:
			
			 /**
		     * @param sizeUnitBean      尺寸单位bean，为空的情况，默认定义单位为dp
		     * @param baseWith          基准宽度，px尺寸
		     * @param baseContentHeight 基准内容高度px尺寸，内容宽度 不包括状态栏！！！！
		     * @param baseWidthDp       基准宽度dp ,dp尺寸
		     * @param baseContentHeightDp      基准内容高度dp，dp尺寸
		     * @param application       上下文
		     * @param listener          计算尺寸的接口
		     */
			LayoutSizeUtil.getInstance().initConfig(1080,1920,480, 800, SizeUnitBean.getDefaultPx(), this,listener);

		**注意:basecontentheight,basecontentheigithdp均指activity也就是content的高度，不包括状态栏高度**

		
****
Activity中的使用：


			
				1：继承base包下的BaseAuto**Activity

				2：自己项目已有BaseActivity，复写setContentView
					   @Override
					    public void setContentView(@LayoutRes int layoutResID) {
					        super.setContentView(layoutResID);
					        IterationContentViewUtil.getInstance().initActivitySize(this,layoutResID);
					    }
					



Fragment中的使用：



			
				1：继承base包下的BaseAutoV4Fragment或BaseAutoNativeFragment

				2：自己项目已有BaseFragment，复写onActivityCreated
					   @Override
					    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
					        super.onActivityCreated(savedInstanceState);
					        IterationContentViewUtil.getInstance().initNativeFragmentSize(this, getLayoutId());
					    }
					




******

针对RecyclerView或者ListView的时候使用：
		
		  @Override
	    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	        TextHolder holder = new TextHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));
			//遍历设置尺寸
	        IterationContentViewUtil.getInstance().initSizeView(context, layoutId, (ViewGroup) holder.itemView);
	        return holder;
	    }

		listView原理同上


针对单个View：
		
	暂时还没添加适配，不好处理，android6.0以上可以通过反射获取到单个View的attributeMap，6.0以下不行，所以暂时没有做适配

	但是可以通过这个方法获取对应的新值
    LayoutSizeUtil.getInstance().getCurrentValue(id);

github地址：
[https://github.com/zhouqihao/AutoLayoutApp](https://github.com/zhouqihao/AutoLayoutApp)


欢迎大家在issues多多提出问题，如果有不足的地方尽管提到issues，也可以进群联系我，吹吹水，QQ群：221061642

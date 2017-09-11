
###实现原理：
		设置基准宽高的px,dp值，获取当前设备的宽高的px，dp值，
		例如 x轴的值，double newvalue =(当前宽度/基准宽度)*具体的值，如此就可以得到适合屏幕比例的值，与设计稿比例保持一致

		android6.0以上设备使用反射的方法，将View的mDebugViewAttributes字段置为true，它在初始化的时候就会去保存属性集合，以键值对的形式
		android6.0以下，5.0设备获取到的属性集合不全，4.4及以下获取不到，所以采用传入布局文件id的形式，解析布局文件，生成对应的ViewBean，解析完布局文件后，遍历contentView，将对应的ViewBean的view地址指向遍历中对应view




####准备工作

	1：在dimens中生成尺寸
		
		x轴的尺寸以：x_ 开头
			：x_10px,x_20px
		y轴的尺寸以：y_ 开头

		字号的尺寸以：t_ 开头

	解析的时候会根据不同的属性名开头，给予不同的比例

		项目中app,test包下dimenUtil可以批量生成

	2：类介绍：


			常用：

				AutoLayout 适配的注解，具体请看注解
				LayoutSizeUtil:初始化的类，以及设置view的尺寸的方法
				IterationContentViewUtil：遍历工具类，遍历viewgroup，调用LaoutSizeUtil设置尺寸的方法
				ReckonSizeListener :自定义的计算尺寸的接口，需在application中初始化传入
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

*****

注解AutoLayout:
	
	参数:isAutoLayout是否自动适配，默认为true
		
****
Activity中的使用：


		一：继承方式
			
				1：继承base包下的BaseAuto**Activity

				2：自己项目已有BaseActivity，复写setContentView
					   @Override
					    public void setContentView(@LayoutRes int layoutResID) {
					        super.setContentView(layoutResID);
					        IterationContentViewUtil.getInstance().initActivitySize(this,layoutResID);
					    }
					


		二:实现方式：

				1：类上添加注解
						@AutoLayout
						public class MainActivity extends BaseAutoAppCompactActivity {
						******
						}
						
				2：onCreate方法上添加注解
						
					    @AutoLayout
					    @Override
					    protected void onCreate(Bundle savedInstanceState) {
					        super.onCreate(savedInstanceState);
					        setContentView(R.layout.activity_main);
					    }


Fragment中的使用：


		一：继承方式
			
				1：继承base包下的BaseAutoV4Fragment或BaseAutoNativeFragment

				2：自己项目已有BaseFragment，复写onActivityCreated
					   @Override
					    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
					        super.onActivityCreated(savedInstanceState);
					        IterationContentViewUtil.getInstance().initNativeFragmentSize(this, getLayoutId());
					    }
					


		二:实现方式：

				1：类上添加注解
						@AutoLayout
						public class MyFragment extends **** {
						******
						}
						
				2：onCreate方法上添加注解
						
					    @AutoLayout
					    @Nullable
					    @Override
					    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
					    ****
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
		
	暂时还没添加适配，不好处理，android6.0以上可以通过反射获取到单个View的attributeMap，6.0以下不行，6.0以下采取解析布局文件，将对应的view与解析到的数据关联起来
		


QQ群：221061642
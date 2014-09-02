package com.sylla.modi;

import com.develop.jawin.GUID;

public interface Constants {

	public static final GUID IID_IMiFont = new GUID("{1ffc9a7b-7606-4c97-8119-78673e9d4821}");

	public static final GUID IID_IMiRect = new GUID("{87d6fb47-7cfa-4db3-82cd-e8dcc5bda7ab}");

	public static final GUID IID_IMiRects = new GUID("{895c88a9-8598-44cc-91c4-8010ae2475eb}");

	public static final GUID IID_IWord = new GUID("{5612275b-b4f1-42af-b696-360d40df041d}");

	public static final GUID IID_IWords = new GUID("{d8363824-4b07-423b-8803-49b470434f3d}");

	public static final GUID IID_ILayout = new GUID("{c300c846-a3fd-4a5b-ad65-4a6ab46b7821}");

	public static final GUID IID_IImage = new GUID("{ac0d48a6-886d-4eb5-a8a1-093d60b9a84a}");

	public static final GUID IID_IImages = new GUID("{77096557-54ca-45c8-81c9-e70c2abaa0fb}");

	public static final GUID IID_IDocument = new GUID("{d4073843-a58a-469a-a8e2-cff3ff77ee4e}");

	public static final GUID DIID__IDocumentEvents = new GUID("{0cf31963-5e4d-4772-ae8d-82fe5a082b26}");

	public static final GUID DIID__IImageEvents = new GUID("{99f3a122-448f-4d36-8243-48bfe32d50b2}");

	public static final GUID IID_IMiSelectRect = new GUID("{ea280c3c-e4b7-42bf-acc8-fe3ad3581638}");

	public static final GUID IID_IMiSelectRects = new GUID("{3a1e1b7a-c041-4ddc-bf3b-042a0b95b82b}");

	public static final GUID IID_IMiSelectableItem = new GUID("{01c4414a-d123-4bc7-a1fa-64e376c01655}");

	public static final GUID IID_IMiSelectableImage = new GUID("{f6379198-3b20-461a-b3a9-191945752557}");

	public static final GUID IID_IMiSearchCallback = new GUID("{8376d508-78ca-416d-a903-2ed62e91e29b}");

	public static final GUID IID_IMiDocView = new GUID("{f958524a-8422-4b07-b69e-199f2421ed89}");

	public static final GUID DIID__IMiDocViewEvents = new GUID("{9028b775-ec59-4118-862a-efdeaf5955a4}");

	public static final GUID IID_IMiDocSearch = new GUID("{bc06ed64-a40c-4fb4-a660-ac6dbc752292}");

	/** enum MiFONT_FAMILY **/
	public static final int miFFamily_Helvetica = 1;
	public static final int miFFamily_Times = 2;
	public static final int miFFamily_Century = 3;
	public static final int miFFamily_TimesCentury = 4;
	public static final int miFFamily_HelveticaCentury = 5;
	public static final int miFFamily_DEFAULT = 6;

	/** enum MiFONT_FACE_STYLE **/
	public static final int miFFace_ROMAN = 1;
	public static final int miFFace_ITALIC = 2;
	public static final int miFFace_BOLD = 3;
	public static final int miFFace_BOLD_ITALIC = 4;

	/** enum MiFONT_SERIF_STYLE **/
	public static final int miFSerifSANS = 1;
	public static final int miFSerifTHIN = 2;
	public static final int miFSerifSQ = 3;
	public static final int miFSerifRND = 4;
	public static final int miFSerifSTYLE_UNKNOWN = 5;

	/** enum MiTHUMBNAIL_SIZE **/
	public static final int miTHUMB_SIZE_TINY = 0;
	public static final int miTHUMB_SIZE_SMALL = 1;
	public static final int miTHUMB_SIZE_MEDIUM = 2;
	public static final int miTHUMB_SIZE_LARGE = 3;
	public static final int miTHUMB_SIZE_MAXIMUM = 3;

	/** enum MiCOMP_LEVEL **/
	public static final int miCOMP_LEVEL_LOW = 0;
	public static final int miCOMP_LEVEL_MEDIUM = 1;
	public static final int miCOMP_LEVEL_HIGH = 2;

	/** enum MiCOMP_TYPE **/
	public static final int miCOMP_UNKNOWN = 0;
	public static final int miCOMP_TIFF_NONE = 1;
	public static final int miCOMP_TIFF_CCITT1D = 2;
	public static final int miCOMP_TIFF_CCITT3 = 3;
	public static final int miCOMP_TIFF_CCITT4 = 4;
	public static final int miCOMP_TIFF_LZW = 5;
	public static final int miCOMP_TIFF_JPEG6 = 6;
	public static final int miCOMP_TIFF_JPEG = 7;
	public static final int miCOMP_TIFF_PACK = 32773;
	public static final int miCOMP_MODI_BLC = 34718;
	public static final int miCOMP_MODI_VECTOR = 34719;
	public static final int miCOMP_MODI_PTC = 34720;

	/** enum MiFILE_FORMAT **/
	public static final int miFILE_FORMAT_TIFF = 1;
	public static final int miFILE_FORMAT_TIFF_LOSSLESS = 2;
	public static final int miFILE_FORMAT_MDI = 4;
	public static final int miFILE_FORMAT_DEFAULTVALUE = -1;

	/** enum MiPRINT_FITMODES **/
	public static final int miPRINT_ACTUALSIZE = 0;
	public static final int miPRINT_PAGE = 1;

	/** enum MiLANGUAGES **/
	public static final int miLANG_CHINESE_SIMPLIFIED = 2052;
	public static final int miLANG_CHINESE_TRADITIONAL = 1028;
	public static final int miLANG_CZECH = 5;
	public static final int miLANG_DANISH = 6;
	public static final int miLANG_DUTCH = 19;
	public static final int miLANG_ENGLISH = 9;
	public static final int miLANG_FINNISH = 11;
	public static final int miLANG_FRENCH = 12;
	public static final int miLANG_GERMAN = 7;
	public static final int miLANG_GREEK = 8;
	public static final int miLANG_HUNGARIAN = 14;
	public static final int miLANG_ITALIAN = 16;
	public static final int miLANG_JAPANESE = 17;
	public static final int miLANG_KOREAN = 18;
	public static final int miLANG_NORWEGIAN = 20;
	public static final int miLANG_POLISH = 21;
	public static final int miLANG_PORTUGUESE = 22;
	public static final int miLANG_RUSSIAN = 25;
	public static final int miLANG_SPANISH = 10;
	public static final int miLANG_SWEDISH = 29;
	public static final int miLANG_TURKISH = 31;
	public static final int miLANG_SYSDEFAULT = 2048;

	/** enum MiActionState **/
	public static final int miASTATE_NONE = 0;
	public static final int miASTATE_PAN = 1;
	public static final int miASTATE_ZOOM = 2;
	public static final int miASTATE_AnntSELECT = 3;
	public static final int miASTATE_HIDDEN = 4;
	public static final int miASTATE_SELECT = 5;

	/** enum MiDocviewMode **/
	public static final int miDOCVIEWMODE_CONTINOUSPAGEVIEW = 0;
	public static final int miDOCVIEWMODE_SINGLEPAGEVIEW = 1;
	public static final int miDOCVIEWMODE_TWOCOLUMNS_CONTINOUSPAGEVIEW = 4;

	/** enum MiVIEWLAYER **/
	public static final int miVIEWLAYER_RICHINK = 0;

	/** enum MiFITMODE **/
	public static final int miFree = 0;
	public static final int miByWidth = 1;
	public static final int miByHeight = 2;
	public static final int miByWindow = 3;
	public static final int miByTextWidth = 4;

	public static final GUID LIBID_MODI = GUIDUtil.getGUID(0xa5ededf4, 0x2bbc, 0x45f3, new int[]{0x82,0x2b,0xe6,0x0c,0x27,0x8a,0x1a,0x79});

	public static final GUID CLSID_Document = GUIDUtil.getGUID(0x40942a6c, 0x1520, 0x4132, new int[]{0xbd,0xf8,0xbd,0xc1,0xf7,0x1f,0x54,0x7b});

	public static final GUID CLSID_Layout = GUIDUtil.getGUID(0x1afc4117, 0x128e, 0x4314, new int[]{0x9d,0x53,0x64,0xcb,0xda,0x5c,0x7e,0x02});

	public static final GUID CLSID_Image = GUIDUtil.getGUID(0x56f963ec, 0x6efc, 0x4a6b, new int[]{0x9a,0x1e,0x5b,0xfe,0x54,0x5c,0x89,0xd0});

	public static final GUID CLSID_Word = GUIDUtil.getGUID(0x5995e30f, 0x59eb, 0x42ae, new int[]{0xbd,0xcf,0x89,0x08,0x27,0x1c,0x0b,0x32});

	public static final GUID CLSID_MiFont = GUIDUtil.getGUID(0xc5eae79b, 0xaccc, 0x4e51, new int[]{0x8e,0xd5,0xa1,0x6f,0x42,0xfb,0x7b,0x1f});

	public static final GUID CLSID_Words = GUIDUtil.getGUID(0x72acbce3, 0x9067, 0x4d5e, new int[]{0x95,0x39,0x34,0x97,0xfe,0xcd,0x03,0x2d});

	public static final GUID CLSID_Images = GUIDUtil.getGUID(0xf961d185, 0x2cc1, 0x43db, new int[]{0x80,0xab,0x56,0x7f,0xa1,0x02,0x51,0xec});

	public static final GUID CLSID_MiRect = GUIDUtil.getGUID(0x00607e44, 0x15df, 0x49bc, new int[]{0xaf,0x0c,0xa9,0x02,0x3e,0x06,0x07,0x91});

	public static final GUID CLSID_MiRects = GUIDUtil.getGUID(0xcb2fbd52, 0x56c1, 0x4dd8, new int[]{0x89,0x7f,0x96,0x1d,0x15,0x43,0xb8,0x77});

	public static final GUID CLSID_MiDocView = GUIDUtil.getGUID(0xef347a62, 0xba21, 0x42e4, new int[]{0x94,0xa0,0x1c,0x0a,0x6d,0x7f,0xdf,0xe7});

	public static final GUID CLSID_MiDocSearch = GUIDUtil.getGUID(0xfa554db9, 0xc806, 0x46a9, new int[]{0xae,0xf8,0xb5,0xaf,0x89,0x1a,0x94,0xf9});

	public static final GUID CLSID_MiSelectRect = GUIDUtil.getGUID(0xa4d2b51f, 0x809f, 0x4c53, new int[]{0x91,0x47,0x55,0x16,0x87,0x18,0x16,0xbd});

	public static final GUID CLSID_MiSelectRects = GUIDUtil.getGUID(0x23e9e94b, 0xc202, 0x4e9c, new int[]{0x8c,0x2e,0xf5,0x3b,0xc5,0x36,0xff,0x1d});

}

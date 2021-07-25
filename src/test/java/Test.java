import eu.diamondcoding.sig4restapi.utils.UrlUtils;

//
// this is just a temporary testing class to test stuff. this is not really a test driven project.
//

public class Test {


    public static void main(String[] args) {
        UrlUtils urlUtils = new UrlUtils("https://iam.amazonaws.com/documents%20and%20settings/?Action=ListUsers&Version=2010-05-08");
        System.out.println(urlUtils.extractCanonicalURI());
        System.out.println(urlUtils.extractCanonicalQueryString());
    }

}

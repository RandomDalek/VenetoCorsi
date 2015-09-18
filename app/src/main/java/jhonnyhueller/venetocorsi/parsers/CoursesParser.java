package jhonnyhueller.venetocorsi.parsers;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import jhonnyhueller.venetocorsi.models.Course;
import jhonnyhueller.venetocorsi.models.CoursePage;
import jhonnyhueller.venetocorsi.models.Lesson;

/**
 * Created by jhonny
 */
public class CoursesParser {
    private final String TAG=getClass().getSimpleName();

    private CoursePage coursePage;
    private Course course;
    private Lesson lesson;

    public CoursesParser() {
    }
    public void parseXml(String url){
        Document doc;
        try{
            doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
            Element root=doc.getDocumentElement();
            vDebug("root element: "+root.getNodeName());
            NodeList listCourseElements =root.getChildNodes();
            for (int i=0;i< listCourseElements.getLength();i++){
                Node CourseNode = listCourseElements.item(i);
                if (CourseNode.getNodeType()==Node.ELEMENT_NODE){
                    course=new Course();
                    Element courseElement= (Element) CourseNode;
                    NodeList listCourseInformation=courseElement.getChildNodes();
                    for (int j=0;j<listCourseInformation.getLength();j++){
                        Node infoNode =listCourseElements.item(j);
                        if (infoNode.getNodeType()==Node.ELEMENT_NODE){
                            Element infoElement= (Element) infoNode;
                            String tag=infoElement.getTagName();
                            String value=infoElement.getNodeValue();
                            vDebug("___tag: "+tag+" contenuto: "+value);
                            if (tag.equals(Course.ID_CORSO)){
                                course.setId_corso(Long.parseLong(value));
                            }else if (tag.equals(Course.DESCRIZIONE_CORSO)){
                                course.setDescrizione_corso(value);
                            }else if (tag.equals(Course.ISCRITTI_CORSO)){
                                course.setIscritti_corso(Short.parseShort(value));
                            }else if (tag.equals(Course.ISCRITTI_MASSIMI)){
                                course.setIscritti_massimi(Short.parseShort(value));
                            }else if (tag.equals(Course.STATO)){
                                course.setStato(Short.parseShort(value));
                            }else if (tag.equals(Course.TITOLO_CORSO)){
                                course.setTitolo_corso(value);
                            }
                        }
                    }

                }
            }
        }catch (Exception e){

        }
    }
    public CoursePage getCoursePage() {
        return coursePage;
    }

    public static void vDebug(String debugString){
        Log.v("CoursesParser",debugString);
    }
    public static void eDebug(String debugString){
        Log.e("CoursesParser",debugString);
    }
}

package sample;

public class ControllerInterface implements AbstractControllerInterface{

    private Controller controller;

    ControllerInterface(Controller controller){
        this.controller = controller;
    }

    @Override
    public void showAlertDialog(String header, String content, boolean resetApplication) {
        controller.showAlertDialog(header, content, resetApplication);
    }
}

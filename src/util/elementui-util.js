import {Message} from 'element-ui'

export default class elementUiUtil {
  static Message (message, type) {
    Message({
      showClose: true,
      message: message,
      type: type
    })
  }
}

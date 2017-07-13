/*
 * Copyright 2017 Software Engineering and Synthesis Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sesygroup.choreography.web.common.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.imgscalr.Scalr;

import com.sesygroup.choreography.web.business.BusinessException;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class Utility {

   public static Date actualDate() {
      return new Date();
   }

   public static byte[] cropImage(byte[] image, int x, int y, int height, int width, boolean flipHorizontally,
         boolean flipVertically, int rotate) throws BusinessException {
      InputStream imageInputStream = new ByteArrayInputStream(image);
      BufferedImage bufferedImage;
      try {
         bufferedImage = ImageIO.read(imageInputStream);
      } catch (IOException e) {
         throw new BusinessException("Error to read the image", e);
      }

      if (bufferedImage == null){
         throw new BusinessException("Error to read the image, please make sure to have an image file");
      }

      switch (rotate) {
      case 90:
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_90, Scalr.OP_ANTIALIAS);
         break;
      case 180:
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_180, Scalr.OP_ANTIALIAS);
         break;
      case 270:
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_270, Scalr.OP_ANTIALIAS);
         break;
      case -90:
         // -90 degree = Rotation.CW_90 + Rotation.FLIP_HORZ +
         // Rotation.FLIP_VERT
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_90, Scalr.OP_ANTIALIAS);
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_HORZ, Scalr.OP_ANTIALIAS);
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_VERT, Scalr.OP_ANTIALIAS);
         break;
      case -180:
         // -180 degree = Rotation.FLIP_HORZ + Rotation.FLIP_VERT
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_HORZ, Scalr.OP_ANTIALIAS);
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_VERT, Scalr.OP_ANTIALIAS);
         break;
      case -270:
         // -270 degree = Rotation.CW_270 + Rotation.FLIP_HORZ +
         // Rotation.FLIP_VERT
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.CW_270, Scalr.OP_ANTIALIAS);
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_HORZ, Scalr.OP_ANTIALIAS);
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_VERT, Scalr.OP_ANTIALIAS);
         break;
      }

      if (flipHorizontally) {
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_HORZ, Scalr.OP_ANTIALIAS);
      }

      if (flipVertically) {
         bufferedImage = Scalr.rotate(bufferedImage, Scalr.Rotation.FLIP_VERT, Scalr.OP_ANTIALIAS);
      }

      bufferedImage = Scalr.crop(bufferedImage, x, y, width, height, Scalr.OP_ANTIALIAS);
      ByteArrayOutputStream croppedImageOutputStream = new ByteArrayOutputStream();

      try {
         ImageIO.write(bufferedImage, "jpg", croppedImageOutputStream);
      } catch (IOException e) {
         throw new BusinessException("Error to get a ByteArrayOutputStream of the cropped image", e);
      }

      return croppedImageOutputStream.toByteArray();
   }

   @SuppressWarnings("unchecked")
   public static <T> Collection<T> convertTo(Collection<Serializable> collection, Class<T> type){
      Collection<T> elements = new LinkedList<T>();
      for (Serializable serializable : collection){
         Validate.isInstanceOf(type, serializable, "impossible to convert %s to %s", serializable.getClass(), type);
         elements.add((T)serializable);
      }
      return elements;
   }
}

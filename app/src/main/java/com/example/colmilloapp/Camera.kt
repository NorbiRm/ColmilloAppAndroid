package com.example.colmilloapp


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.os.AsyncTask.execute
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.os.Environment.getExternalStorageDirectory
import android.graphics.Bitmap
import android.os.Environment
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.colmilloapp.Models.Foto
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_camera.*
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val REQUEST_CAMERA = 1888
private val GALLERY_REQUEST = 1889
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Camera.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Camera.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Camera : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var getFotoButton:Button? = null
    private var subirFotoButton:Button? = null
    private var fotoDescription:TextInputEditText? = null
    private var imageView:ImageView?=null
    private var imageFoto:Bitmap?=null
    private var storage:FirebaseStorage? = null
    private var database:FirebaseDatabase? = null

    //Camera Config



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFotoButton = view!!.findViewById(R.id.getFoto) as Button
        getFotoButton!!.setOnClickListener{
            getFoto()
        }

        subirFotoButton = view!!.findViewById(R.id.upload)
        subirFotoButton!!.setOnClickListener{
            uploadImage()
        }


        imageView = view!!.findViewById(R.id.imageTakenView) as ImageView

        fotoDescription = view!!.findViewById(R.id.descriptionFoto) as TextInputEditText

        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Camera.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Camera().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //-----------------------------------------------------------------------------------------------------------------

    fun getFoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        Log.i("Camera Fragment","getFoto function")

        startActivityForResult(intent, REQUEST_CAMERA)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("Camera Fragment","Camera Requested")

        if (requestCode == REQUEST_CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            val bytes = ByteArrayOutputStream()
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val destination = File(getExternalStorageDirectory(), "temp.jpg")
            val fo: FileOutputStream
            try {
                fo = FileOutputStream(destination)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            imageView!!.setImageBitmap(thumbnail)

            imageFoto = thumbnail

            //uploadFileToServerTask().execute(destination.getAbsolutePath())
        }
    }

    fun uploadImage(){
        if (imageFoto!=null){
            // Create a storage reference from our app
            val storageRef = storage!!.getReference("uploadedFotos")
            val time = System.nanoTime()
            val id = FirebaseAuth.getInstance().uid
            val imageID = id + time + ".jpg"
            val imageRef = storageRef.child(imageID)


            val bitmap = imageFoto
            val baos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()


            //Upload to firebaseStorage
            var uploadTask = imageRef.putBytes(data)



            uploadTask.addOnFailureListener {

            }.addOnSuccessListener {

                imageView!!.setImageBitmap(null)
                Toast.makeText(super.getContext(),"Foto uploades",Toast.LENGTH_SHORT).show()



                var foto:Foto = Foto()

                var storageRef = storage!!.getReference("uploadedFotos").child(imageID)
                var urlImg = ""

                storageRef.getDownloadUrl().addOnSuccessListener(OnSuccessListener() {

                        urlImg = it.toString()

                        foto.likes = 0
                        foto.imageURL = urlImg
                        foto.idUser = id.toString()
                        foto.descripcion = descriptionFoto.text.toString()

                        var fotosRef = database!!.getReference("Fotos")

                        foto.idFoto = fotosRef.push().key.toString()

                        var fotos: MutableList<Foto> = ArrayList<Foto>()
                        //fotos.add(foto)
                        fotosRef.child(foto.idFoto).setValue(foto)

                        Log.i("ImagesUpload","URL" + urlImg)

                }).addOnFailureListener(OnFailureListener() {
                    @Override fun onFailure(@NonNull exception:Exception) {
                        Log.i("ImagesUpload","Fallo URL")
                    }
                })


            }





            }else{
            Toast.makeText(super.getContext(),"Take a foto first",Toast.LENGTH_SHORT).show()
        }

    }
}

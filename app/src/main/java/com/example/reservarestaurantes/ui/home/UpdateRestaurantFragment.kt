package com.example.reservarestaurantes.ui.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reservarestaurantes.R
import com.example.reservarestaurantes.databinding.FragmentUpdateRestaurantBinding
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.viewmodel.RestaurantViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UpdateRestaurantFragment : Fragment() {

    //Se decriben los parametros pasados por argumento
    private val args by navArgs<UpdateRestaurantFragmentArgs>()

    // creacion de variables para firebase, almacenamiento de imagenes
    private var progressDialog: ProgressDialog? = null
    private val PICK_IMAGE_REQUEST = 71
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    //declaración de binding
    private var _binding: FragmentUpdateRestaurantBinding? = null
    private val binding get() = _binding!!
    // declaracion de viewModel
    private lateinit var restaurantViewModel: RestaurantViewModel
    private val usuario = Firebase.auth.currentUser?.email.toString()

    //url de imagen
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // instancia de viewModel
        restaurantViewModel =
            ViewModelProvider(this)[RestaurantViewModel::class.java]
        //instancia binding
        _binding = FragmentUpdateRestaurantBinding.inflate(inflater, container, false)

        //instancia de firebase
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        //Coloco la info del restaurante en los campos del fragmento... para modificar...
        binding.iptName.setText(args.restaurant.name)
        binding.iptTel1.setText(args.restaurant.phone1.toString())
        binding.iptTel2.setText(args.restaurant.phone2.toString())
        binding.iptPriceRange.setText(args.restaurant.priceRange)
        binding.iptSchedule1.setText(args.restaurant.scheduleInit.toString())
        binding.iptSchedule2.setText(args.restaurant.scheduleEnd.toString())
        binding.iptNumberReservations.setText(args.restaurant.reservationNumber.toString())
        binding.iptAddress.setText(args.restaurant.Address)

        //asignacion de click listener
        binding.btnDeleteRestaurant.setOnClickListener { deleteRestaurant() }
        binding.btnUpdateRestaurant.setOnClickListener { updateRestaurant() }
        binding.btnChooseImage.setOnClickListener { launchGallery() }
        binding.btnUploadImage.setOnClickListener { uploadImage() }
        binding.btnBooking.setOnClickListener { goToBooking() }

        /*
            si el usuario que lo creó no es el mismo al usuario que fue ingresado,
            los campos editables dejan de serlo y se ocultan los botones de actualización
         */
        if(args.restaurant.userCreate != usuario){

            binding.iptName.isFocusable = false
            binding.iptTel1.isFocusable = false
            binding.iptTel2.isFocusable = false
            binding.iptPriceRange.isFocusable = false
            binding.iptSchedule1.isFocusable = false
            binding.iptSchedule2.isFocusable = false
            binding.iptNumberReservations.isFocusable = false
            binding.iptAddress.isFocusable = false
            binding.btnDeleteRestaurant.isVisible = false
            binding.btnChooseImage.isVisible = false
            binding.btnUploadImage.isVisible = false
            binding.btnUpdateRestaurant.isVisible = false

        }
        setHasOptionsMenu(true)

        return binding.root
    }

    // ir al fragmento de reservas en el momento que el botón "realizar reservas" es clickeado
    private fun goToBooking() {
        val action = UpdateRestaurantFragmentDirections.actionUpdateRestaurantFragmentToAddReservasFragment(args.restaurant)
        findNavController().navigate(action)
    }

    // abrir galeria de imagenes
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // asignación de la imagen seleccionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        this.imageUri = data?.data
    }

    // subir imagen a firestorage
    private fun uploadImage() {
        val name = binding.iptName.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("imagenes/$usuario/$name")

        this.imageUri?.let {
            storageReference
                .putFile(it)
                .addOnFailureListener {
                    Toast.makeText(requireContext(),getString(R.string.fail_save),Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(requireContext(),getString(R.string.success_save),Toast.LENGTH_SHORT).show()
                }
        }
    }

    // visualización del menú con la opcion de poder eliminar el restaurante
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    // Opcion seleccionada del menu de opciones
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.menu_delete) {
            deleteRestaurant()
        }
        return super.onOptionsItemSelected(item)
    }

    // Es el método que me eimina el restaurant e por medio del id que se pasa como argumentos.
    private fun deleteRestaurant() {
        val pantalla= AlertDialog.Builder(requireContext())

        pantalla.setTitle(R.string.delete)
        pantalla.setMessage(getString(R.string.seguroBorrar)+" ${args.restaurant.name}?")

        pantalla.setPositiveButton(getString(R.string.si)) { _,_ ->
            restaurantViewModel.deleteRestaurant(args.restaurant)
            findNavController().navigate(R.id.action_updateRestaurantFragment_to_nav_restaurant)
        }

        pantalla.setNegativeButton(getString(R.string.no)) { _,_ -> }
        pantalla.create().show()
    }

    // metodo de actualización de restaurante
    private fun updateRestaurant() {
        val name=binding.iptName.text.toString()
        val phone1= Integer.parseInt(binding.iptTel1.text.toString())
        val phone2= Integer.parseInt(binding.iptTel2.text.toString())
        val prices = binding.iptPriceRange.text.toString()
        val scheduleInit = Integer.parseInt(binding.iptSchedule1.text.toString())
        val scheduleEnd = Integer.parseInt(binding.iptSchedule1.text.toString())
        val reservationTime = Integer.parseInt(binding.iptNumberReservations.text.toString())
        val address = binding.iptAddress.text.toString()
        val googleAddress = this.imageUri.toString()

        if (name.isNotEmpty()) {
            val restaurant= Restaurant(args.restaurant.id, name, phone1, phone2, prices, scheduleInit,
                scheduleEnd, reservationTime, address, usuario,googleAddress)

            restaurantViewModel.saveRestaurant(restaurant)

            Toast.makeText(requireContext(),getString(R.string.msg_nft_update), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateRestaurantFragment_to_nav_restaurant)
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),getString(R.string.msg_restaurant_error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
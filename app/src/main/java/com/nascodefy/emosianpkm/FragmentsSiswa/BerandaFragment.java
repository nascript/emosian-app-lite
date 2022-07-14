package com.nascodefy.emosianpkm.FragmentsSiswa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.nascodefy.emosianpkm.Adapter.HistoryAdapter;
import com.nascodefy.emosianpkm.Adapter.JournalAdapter;
import com.nascodefy.emosianpkm.ArticleViewActivity;
import com.nascodefy.emosianpkm.ArticleViewActivityCorausel;
import com.nascodefy.emosianpkm.DiagnosisPHQActivity;
import com.nascodefy.emosianpkm.HistoryPHQActivity;
import com.nascodefy.emosianpkm.Model.Article;
import com.nascodefy.emosianpkm.Model.History;
import com.nascodefy.emosianpkm.Model.Journal;
import com.nascodefy.emosianpkm.Model.User;
import com.nascodefy.emosianpkm.R;
import com.nascodefy.emosianpkm.TellMeActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

//import com.nascodefy.emosian.ConsultActivity;


public class BerandaFragment extends Fragment {

    CardView diagnosaPHQ, historyPHQ;
    //CarouselView carouselView;
    FirebaseAuth auth;

    private Activity mActivity;

    private JournalAdapter journalAdapter;
    private List<Journal> mJournal;

    CircleImageView image_profile;
    TextView username;
    DatabaseReference reference,Userreference;
    FirebaseUser fuser;

    Button tellMe;

    RecyclerView recyclerViewJournal;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;


    Spinner spinnerFeel;
    TextView descFeel;
    Button btn_submit_feel;

    String dateToday;
    Calendar calender;
    SimpleDateFormat simpleDateFormat;



    //int[] sampleImages = {R.drawable.img_bann_1_new, R.drawable.img_bann_2_new, R.drawable.seputar_depresi, R.drawable.ibadah};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda,  container,false);

        btn_submit_feel = view.findViewById(R.id.btn_submit_feel_sheet);

        recyclerViewJournal = view.findViewById(R.id.recycler_view_journal);
        recyclerViewJournal.setHasFixedSize(true);
        recyclerViewJournal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        auth = FirebaseAuth.getInstance();

        diagnosaPHQ = view.findViewById(R.id.to_consult);
        historyPHQ = view.findViewById(R.id.to_histories);
        image_profile = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);

//        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);

        tellMe = view.findViewById(R.id.tell_me_today);

//        carouselView.setImageListener(imageListener);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        Userreference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        Log.d("BerandaSiswa", "UID : " + fuser.getUid());



        diagnosaPHQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toConsult = new Intent(getActivity(), DiagnosisPHQActivity.class);
                toConsult.putExtra("username",username.getText().toString());
                startActivity(toConsult);
            }
        });

        historyPHQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHistory = new Intent(getActivity(), HistoryPHQActivity.class);
                startActivity(toHistory);
            }
        });

//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                //Toast.makeText(getActivity(), "Clicked item: "+ position, Toast.LENGTH_SHORT).show();\
//
//                String title = "";
//                String description;
//
//                if (position == 0) {
//                    int img = R.drawable.img_bann_1_new;
//                    title = "Mengenal Metode Self Healing  Butterfly Hug";
//                    description = "Mari merapat para pencinta drama Korea !" + "\n \n" +
//                            "Cuplikan di atas tentu terasa tidak asing bagi teman-teman penikmat drama Its Okay Not To be Okay. " +
//                            "Ada hal yang menarik pada drama tersebut yaitu adegan ketika si tampan Kim Soo Hyun yang memerankan " +
//                            "Gang tae  mengarahkan Ko Moon Young (So Ye Ji) agar memeluk dirinya sendiri, " +
//                            "untuk menangkan dan redakan emosinya. \n\n" + "Lalu munculah suatu pertanyaan, sebenarnya apasih yang mereka lakuin? \n\n" +
//                            "Hal yang dilakukan oleh pemeran wanita tersebut dikenal dengan istilah butterfly hug. Butterfly hug merupakan salah " +
//                            "satu metode self healing yang pertama kali kemukakan oleh Lucina Artigas untuk korban badai Pauline di Mexico tahun 1998. " +
//                            "Butterfly hug digunakan pada proses " +
//                            "pemulihan trauma, dengan tujuan sebagai penenang ketika sedang merasakan emosi negatif yang kuat. \n\n" +
//                            "Setiap orang tanpa terkecuali dapat mengalami emosi negatif yang disebabkan oleh tekanan dari lingkungan " +
//                            "sekitar ataupun yang berasal dari diri sendiri, kumpulan dari emosi tersebut dapat menyebabkan terjadinya depresi. " +
//                            "Salah satu penanggulangan ketika mengalami " +
//                            "kumpulan emosi yang meledak-ledak ini adalah dengan melakukan butterfly hug. \n\n" +
//                            "Lalu kenapa sih butterfly hug bisa mengatasi kecemasan ? \n\n" +
//                            "Saat seseorang mengalami stress, trauma ataupun gangguan kecemasan tubuh secara otomatis juga akan ikut bereaksi." +
//                            " Respon fisik tersebut berupa tubuh yang menjadi lebih tengang, butterfly hug menjadi salah satu solusi untuk mengambalikan tubuh " +
//                            "menjadi rileks kembali. Association for Comprehensive Energy Psychology, mengatakan bahwa dengan mengaplikasikan " +
//                            "butterfly hug dapat membuat jantung terasa lebih lapang, menyeimbangkan otak kiri dan kanan." +
//                            "Berikut cara melakukan butterfly hug: \n\n" +
//                            "1. Silangkan kedua tangan di dada, hingga kedua ujung jari tengah berada di bahu. \n\n" +
//                            "2. Lakukan tepukan berulang pada bahu secara perlahan. \n\n" +
//                            "3. Barengi gerakan tersebut dengan menarik napas dalam dan hembuskan secara perlahan. \n\n" +
//                            "4. Ulangi gerakan hingga tubuh merasa lebih tenang dan rileks,  (pada umumnya dilakukan selama 2-3 menit).\n\n" +
//                            "Pada jurnal The Butterfly hug Implementing EMDR Early Mental Health Interventions for Man-Made and Natural Disasters (Artigas, 2014)  " +
//                            "diketahui bahwa selama 23 tahun terakhir korban trauma yang menunjukkan gejala emosi dengan pemicu internal " +
//                            "(kilas balik masa lalu dan pikiran negatif), melakukan metode self healing butterfly hug  sebagai " +
//                            "bentuk terapi perilaku individu dengan cara relaksasi dapat membuat korban menjadi lebih tenang. \n\n" +
//                            "Butterfly hug  dapat menenangkan dan meredakan gejolak emosi yang dirasakan, namun tidak serta merta bisa digunakan sebagai " +
//                            "pengganti terapi. Bila terus-menerus merasakan gejolak emosi negatif jangan sungkan untuk melakukan konsultasi " +
//                            "dengan pihak yang lebih professional seperti psikolog, ataupun dapat menghubungi " +
//                            "Bimbingan Konseling (BK) dan Pusat Informasi dan Konseling Remaja (PIK R). \n\n";
//
//                    //Article article = new Article();
//                    Intent toArticle = new Intent(getActivity(), ArticleViewActivityCorausel.class);
//                    toArticle.putExtra("img_article", img);
//                    //toArticle.putExtra("img_article", article.getImageURL());
//                    toArticle.putExtra("title", title);
//                    toArticle.putExtra("description", description);
//                    startActivity(toArticle);
//
//
//                } else if (position == 1) {
//                    int img = R.drawable.img_bann_2_new;
//                    title = "Aktivitas yang Mengurangi Depresi";
//                    description = "Energi positif sangat penting untuk menjadi asupan pertama bagi penderita depresi. Mencintai diri sendiri " +
//                            "dengan merawat kebugaran dan kesehatan tubuh salah satunya. Saat kalian depresi, cobalah untuk tersenyum, " +
//                            "mencoba bergerak, dan lakukan hal-hal baik dibawah in yuk \n\n" +
//
//                            "1. Jalan Cepat \n\n" +
//                                "Berjalan cepat sudah dikatakan sebagai olahraga. Berjalan cepat ini bisa menggantikan obat antidepresan yang dikonsumsi penderita " +
//                                "depresi. Lakukan jalan cepat dengan tidak terburu-buru serta konsentrasi niat yang penuh. Fokuslah pada kondisi fisik, " +
//                                "bukan pemandangan alam dan udara segar. Nikmati setiap pergantian kaki yang menapak, detak jantung terus bertambah cepat hingga nyeri yang terasa.\n" +
//                                "Jika kita terhanyut pada rasa cemas dan tidak nyaman, saat berjalan cepat kita bisa mengalihkan fokus pada pernapasan. " +
//                                "Pikiran yang awalnya negatif menjadi lebih berkurang saat kita berjalan cepat.\n\n" +
//
//                             "2. Berlari" + "\n\n" +
//                            "Berlari kecil memberikan rangsangan hampir ke seluruh tubuh kita dari kepala, leher, pundak, lengan, pinggang, kaki, sampai jantung. Aliran darah yang terjadi saat jogging, " +
//                                    "dapat membuat pasien lebih tenang menghadapi stres dan mampu meregangkan otot. Senyawa kimia beta endorfin banyak keluar dari dalam tubuh sehingga meningkatkan mood. \n\n" +
//                                    "Ketika jogging, lakukan peregangan otot terlebih dahulu pada seluruh anggota tubuh. Saat mulai berlari, ambillah dengan " +
//                                    "gerakan kecil dahulu, setelah setengah perjalanan baru mengambil kecepatan lari sesuai kemampuan kita. Waktu terbaik yang " +
//                                    "disarankan yaitu sekitar pukul 05.30 - 07.00 atau 16.30-18.00. Latihan dipusatkan pada pernapasan dan irama jantung yang berubah. " +
//                                    "Saat kita memusatkan perhatian pada tubuh, kkita akan melupakan perasaan khawatir tentang masa depan ataupun kecemasan lain.\n\n" +
//
//                                    "3. Satu Hari Spesial \n\n" +
//                                    "Dalam satu minggu, pasti terdaapt hari yang paling melelahkan dan biasa saja untuk kalian. Saat kegiatan belajar-menagajar di sekolah, biasanya siswa " +
//                                    "akan paling membenci hari Senin. Energi kita akan terkuras habis dan perasaan bosan bisa mengelilingi setelahnya. \n\n" +
//                                    "Perasaan bosan itu bisa dicegah dengan memberikan satu hari saja untuk mentraktir diri kalian dengan hal yang kalian sukai, semisal membeli makanan " +
//                                    "favotit tanpa ada batasan sekalipun. Perasaan kalian akan membaik setelah makan makanan  itu sehingga momen melalahkan" +
//                                    " pun bisa berkurang. Perasaan ini bisa kalian simpan dalam memori dan dapat kalian hadirkan saat jadwal padat menerpa di hari Senin. " +
//                                    "Kalian akan mengatakan “aku tidak apa-apaa sedikit lelah, besok Minggu aku akan makan enak”.\n\n" +
//
//                                    "4. Puji Diri Kita \n\n" +
//
//                                    "Ketika menjelang tidur, ucapkanlah terima kasih dan permintaan maaf selalu untuk tubuh ini. Berikan rasa syukur hadir saat kalian telah melalui hari dengan " +
//                                    "baik ataupun buruk. Tanamkan pada diri bahwa kalian sudah cukup berusaha sebaik mungkin. Kata yang bisa kalian gunakan antara lain :\n" +
//                                    "“Kau telah melakukan hal yang keren hari ini, terima kasih banyak”\n" +
//                                    "“Mulutku, maaf hari ini telah memaksamu berbicara terlalu banyak, terima kasih hari ini sudah bekerja keras”\n" +
//                                    "“Aku menyayangi semua yang ada pada diriku, mari kita bersama beristirahat sejenak.”\n" +
//                                    "Kata-kata diatas bisa kalian ubah dalam versi kalian sendiri sehingga lebih mengena dan teringat lama. Memuji diri sendiri " +
//                                    "ini akan mendatangkan perasaan yang lebih tenang dan damai sehingga tidue lebih nyenyak menghadapi esok yang baru.\n\n";
//
//                                Intent toArticle = new Intent(getActivity(), ArticleViewActivityCorausel.class);
//                                toArticle.putExtra("img_article", img);
//                                toArticle.putExtra("title", title);
//                                toArticle.putExtra("description", description);
//                                startActivity(toArticle);
//                } else if (position == 2) {
//                    int img = R.drawable.seputar_depresi;
//                    title = "Hal Yang Perlu Kamu Ketahui Tentang Depresi";
//                    description = "Stereotip negatif merundung orang yang mengidap penyakit mental, salah satunya depresi. Akibat stereotip ini, banyak orang yang enggan mengakui dirinya sedang depresi. Kebanyakan penderita akan merasa takut dihakimi dan dihindari sehingga memilih untuk bungkam. Hal ini tentu menjadi atmosfer yang sangat tidak baik bagi penderita. Jika lama terpendam, rasa bunuh diri akan muncul. Sebagai orang yang hidup dalam lingkungan penderita, kita sudah seharusnya memahami beberapa hal dibawah untuk mendukung proses perbaikan diri penderita.\n\n" +
//                                    "Terjadi pada Semua Orang\n" +
//                                    "Selayakanya demam, depresi merupakan penyakit jiwa yang dapat terjadi pada siapa saja. Menderita depresi bukanlah suatu aib yang memalukan karena depresi merupakan reaksi mental dan fisik dari suatu hal yang tidak kita prediksi sebelumnya. \n" +
//                                    "Merasa sedih, gelisah, atau tidak ingin diganggu orang lain dalam 1-2 hari pasti pernah kita rasakan. Hal ini menjadi salah satu ciri depresi yang normal saja dirasakan semua orang. Saat merasa seperti ini, kita harus mencari penghiburan dari diri sendiri maupun orang lain sehingga tidak menjadi berlarut-larut menuju tingkat yang berat.\n\n" +
//                                    "Depresi Berawal dari Stres \n" +
//                                    "Ketika kita diberi tugas untuk presentasi deihadapan banyak orang, kita bisajadi akan stres. Saat kita mengelola stres ini dengan belajar tekun ataupun berlatih, maka stres ini akan berkurang. Seseorang yang mampu mengelola stres dengan baik ini akan membawa dirinya pada kesuksesan serta menjaga jiwanya tetap seimbang.\n" +
//                                    "Rasa gelisah maupun cemas adalah contoh stres pada mental yang dirasakan manusia. Ketika dihadapkan pada kondisi tertekan seperti itu juga, terkadang kita pun merasa nyeri perut, mulas, ataupun jantung berdebar kencang. Ketika tugas presentasi itu kita biarkan menjadi beban, rasa gelisah terus melingkupi kita juga , maka kita akan terjerumus pada depresi.\n\n" +
//                                    "Depresi Dipengaruhi Genetik\n" +
//                                    "Seseorang yang memiliki orang tua mudah cemas, perfeksionis, suka grusa-grusu, akan memiliki kecenderungan 2 kali lipat untuk memiliki sifat diatas. Rasa cemas yang menurun ini mampu menjadi pemicu timbulnya depresi, belum lagi apabila lingkungan keluarganya juga memiliki sifat tersebut namun tidak bisa mengendalikannya. Depresi ini memengaruhi 15 gen tubuh yang berperan dalam perkembangan saraf otak. \n\n" +
//                                    "Hanya Kalangan Profesional yang Boleh Mengatakan Kamu Depresi\n" +
//                                    "Tes depresi banyak bermunculan di laman internet, tes semacam ini sebenarnya hanya untuk mengukur diawal bagaimana keadaan kamu. Tes ini tidak akan bisa menegakkan bahwa kamu benar-benar depresi.\n" +
//                            "Untuk bisa mengatakan kamu depresi, kamu harus menemui psikolog/psikiater terlebih dahulu. Psikolog/psikiater ini akan menyodorkan berbagai tes, termasuk wawancara dengan orang disekitarmu untuk bisa mengetahui keadaanmu yang sebenarnya. \n";
//
//                    Intent toArticle = new Intent(getActivity(), ArticleViewActivityCorausel.class);
//                    toArticle.putExtra("img_article", img);
//                    toArticle.putExtra("title", title);
//                    toArticle.putExtra("description", description);
//                    startActivity(toArticle);
//
//                } else if (position == 3) {
//                    int img = R.drawable.ibadah;
//                    title = "Jenis-jenis Ibadah yang Membantumu Menenangkan Jiwa";
//                    description = "Kegelisahan pasti sering menimpa teman-teman saat dipenuhi banyak beban seperti tugas sekolah yang menumpuk, memberikan presentasi di depan kelas, belum lagi dihadapkan dengan teman satu kelompok yang tidak membantu sama sekali. Nah, sebagai seorang muslim, ibadah yang kita lakukan setiap hari tuh ternyata bisa membuat kita lebih tenang secara jiwa lho, bukan hanya mendapat pahala. \n\n" +
//                                    "Salat\n" +
//                                    "Salat yang kita lakukan sehari 5 kali ini nih jika kita melakukannya dengan ikhlas penuh pemaknaan, hasilnya sungguh menenangkan lho! Tidak jarang dari kita membuat aktivitas salat ini hanya sebagai ritual biasa, asal sudah sholat saja, maka urusanku dengan Tuhan selesai. \n" +
//                                    "Nah, hal seperti diatas ini sebenarnya hanya membuat rugi, lho. Kita sudah bergerak berkali-kali secara cepat juga mungkin, namun ketenangan batin belum kita raih. Yuk, coba lebih khusyuk lagi sholatnya karena didalam sholat yang khusyuk minimal ada aku dan Allah swt. yang saling berinteraksi. \n\n" +
//                                    "Wudhu\n" +
//                                    "Wudhu yang kita lakukan selama ini bukan hanya bisa menyegarkan dan memnyinarkan aura seseorang lho. Wudhu merupakan ajang pembersihan jiwa kita agar terpancar cahaya serta membawa keheningan hati. Syaratnya ketenangan jiwa ini datang yaitu dengan melakukannya secara perlahan dan penuh pemaknaan.\n" +
//                                    "Saat membasuh wajah, pastikan air yang terkena kulit mampu menghubungkan jiwa dan pikiran kepada Allah swt. semata. Hadirkan jiwa kalian disetiap gerakan tanpa rasa terburu-buru. Rasa buru-buru ini hanya akan menghambat sambung rasa kita kepada Allah swt. \n\n" +
//                                    "Doa\n" +
//                                    "Saat kita ingin sesuatu, cara termudah adalah dengan meminta kepada Sang Kuasa. Ketika berdoa, kita selalu diajarkan untuk merendahkan diri dihadapan Tuhan, melepas semua atribut, pangkat, dan hal duniawi. Berdoa dengan penuh keyakinan serta harapan yang hanya ditujukan pada Allah swt. akan menghadirkan jiwa yang tenang pula setelah berdoa.\n" +
//                                    "Tidak pernah ada batasan seseorang untuk meminta kepada Tuhan, begitupula tak ada batasan seorang hamba menyerahkan diri dan mengakui dirinya hanyalah milik Tuhan. Sesungguhnya dititik pasrah dan mengembalikan semua hal yang akan terjadi ke depan pada Allah swt., segala beban duniawi akan terangkat dan membuatmu lebih tenang. \n\n" +
//                                    "Dzikir\n" +
//                                    "Kegiatan yang bisa dilakukan dimana saja, kapan saja , dan dalam kondisi apapun ini bila dihayati arti serta tidak dilakukan secara terburu-buru akan mejadi media relaksasi bagi para pelantunnya. Frase yang diucapkan berkali-kali ini akan meningkatkan kepercayaan kita kepada Allah swt. sehingga membawa ketenangan dan kenyamanan jiwa.\n" +
//                                     "Dzikir juga menjadi salah satu media memasrahkan diri kepada Allah swt. yang termudah untuk dilakukan saat seseorang tiba-tiba terkena musibah. Ikatan batin dengan Sang Pencipta pun akan terjalin kuat menjadi pengobat hati yang tidak sedang baik saja-saja. \n\n" +
//                                    "Memahami dan Membaca Al-Quran\n" +
//                                    "Al-Quran menjadi bukti bahwa Allah swt. adalah satu-satunya pengatur di dunia ini. Segala yang terjadi sudah menjadi ketetapan-Nya yang tidak bisa dirubah. Membaca dan memahami Al-Quran akan membuat kita paham bahwasannya segala hal yang baik maupun buruk yang tiba-tiba datang pada diri sudah menjadi kuasa-Nya. \n" +
//                            "Dari AL-Quran kita akan mengetahui pula bahwa diri ini bukanlah yang paling besar sehingga tidak selayaknya kita menyombongkan diri dihadapan-Nya. Al-Quran akan membuat kita semakin tunduk, pasrah, dan menyadari bahwa manusia harus selalu berusaha. Allah tidak akan mengubah nikmat atau bencana kecuali jika dirinya sendiri ingin mengubah perasaan, perbuatan, dan kenyataan hidup yang ada. \n\n";
//
//                    Intent toArticle = new Intent(getActivity(), ArticleViewActivityCorausel.class);
//                    toArticle.putExtra("img_article", img);
//                    toArticle.putExtra("title", title);
//                    toArticle.putExtra("description", description);
//                    startActivity(toArticle);
//                }
//
//            }
//        });



        readJournal();
        loadProfileImage();

        tellMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheet();
            }

        });


        return view;
    }

//    public void openBottomSheet1() {

//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                getActivity(), R.style.BottomSheetDialogTheme
//        );
//        View bottomSheetView = LayoutInflater.from(getContext())
//                .inflate(
//                        R.layout.layout_bottom_sheet,
//                        (RelativeLayout) view.findViewById(R.id.bottomSheetContainer)
//
//                );
//
//
////                calender = Calendar.getInstance();
////                simpleDateFormat = new SimpleDateFormat("EEEEEE, MM.dd.yyyy" , Locale.getDefault());
////                dateToday = simpleDateFormat.format(calender.getTime());
////                loadingBar = new ProgressDialog(getActivity());
//        spinnerFeel = view.findViewById(R.id.spinner_feel_sheet);
//
//        //ArrayAdapter<CharSequence> feelItem = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.feel,
//        //R.layout.support_simple_spinner_dropdown_item);
//
//        String items[] = {"Makan", "Minum"};
//
//        spinnerFeel.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items));
////
//        //spinnerFeel.setAdapter(feelItem);
//
//        descFeel = view.findViewById(R.id.feel_desc_sheet);
//
//
//
//        bottomSheetView.findViewById(R.id.btn_submit_feel_sheet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Bisa di Klik", Toast.LENGTH_SHORT).show();
//                bottomSheetDialog.dismiss();
//            }
//        });
//        bottomSheetDialog.setContentView(bottomSheetView);
//        bottomSheetDialog.show();
//    }

    public void openBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.sheet_bottom_layout, null);
        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEEEEE, MM.dd.yyyy" , Locale.getDefault());
        dateToday = simpleDateFormat.format(calender.getTime());

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Journal");
        descFeel = view.findViewById(R.id.feel_desc);

        final Spinner spin1 = (Spinner) view.findViewById(R.id.spin1);
        //Spinner spin2 = (Spinner) view.findViewById(R.id.spin2);
        //ListView catList = (ListView) view.findViewById(R.id.listItems);
        Button btnDone = (Button) view.findViewById(R.id.btnDone);

        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.show();

        String items[] = {"Senang", "Sedih", "Marah", "Kecewa", "Bahagia", "Malas"};

        spin1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items));

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String txt_descfeel = descFeel.getText().toString();
                final String txt_feetTitle = spin1.getSelectedItem().toString();

                if (TextUtils.isEmpty(txt_descfeel) || TextUtils.isEmpty(txt_feetTitle)) {
                    Toast.makeText(getActivity(), "Harap Semua Kolom Di Isi", Toast.LENGTH_SHORT).show();
                } else {
                    addJournal(txt_descfeel, txt_feetTitle);
                }


                mBottomSheetDialog.dismiss();
            }
        });

    }



    private void loadProfileImage(){
        Userreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")){
                    image_profile.setImageResource(R.drawable.img1);
                } else {
                    try{Glide.with(getContext()).load(user.getImageURL()).into(image_profile);}
                    catch (Exception e){e.printStackTrace();}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addJournal(String txt_descfeel, String txt_feetTitle) {

        FirebaseUser firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = format.format(new Date());
        reference = FirebaseDatabase.getInstance().getReference("Journal").child(userid).child(date);



        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", userid);
        hashMap.put("username",username.getText().toString());
        hashMap.put("titleFeel", txt_feetTitle);
        hashMap.put("descFeel", txt_descfeel);
        hashMap.put("dateFeel", dateToday);


        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    readJournal();
                    Toast.makeText(getActivity(), "Jurnal Terkirim", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void readJournal() {

        mJournal = new ArrayList<>();

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Journal").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Journal journal = snapshot.getValue(Journal.class);

                    if (journal.getId().equals(fuser.getUid())) {
                        mJournal.add(journal);
                        Log.d(TAG, "Ambil Data pada Journal: " + journal.getTitleFeel());
                    }

                }

                journalAdapter = new JournalAdapter(getContext(), mJournal);
                recyclerViewJournal.setAdapter(journalAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        final FirebaseUser firebaseUser = auth.getCurrentUser();
//        final String userid = firebaseUser.getUid();
//        reference = FirebaseDatabase.getInstance().getReference("Journal").child(userid);
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String date = format.format(new Date());


//        reference = FirebaseDatabase.getInstance().getReference("Journal").child(userid).child(date);
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                Journal journal = dataSnapshot.getValue(Journal.class);
//                if (!journal.getId().equals(firebaseUser.getUid())) {
//                    mJournal.add(journal);
//                } else {
//                    Toast.makeText(getActivity(), "Journal Kosong", Toast.LENGTH_SHORT).show();
//                }
//
//                Log.d(TAG, "Ambil Data pada Journal: " + journal.getTitleFeel());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//            // ...
//        });


    }

//    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//        }
//    };


//    public void tellMe(View view) {
//        Intent toTell = new Intent(getActivity(), TellMeActivity.class);
//        startActivity(toTell);
//    }
}

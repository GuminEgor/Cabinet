package com.lymno.cabinet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;
import com.mikepenz.octicons_typeface_library.Octicons;

public class MainActivity extends AppCompatActivity {

    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        //final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460").withIdentifier(1);;
        //final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile adultProfile = new ProfileDrawerItem().withName("����� ������").withEmail("marie2342@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        adultProfile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("���������� ���������").withIcon(GoogleMaterial.Icon.gmd_settings),
                        new ProfileSettingDrawerItem().withName("����� �� ��������").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING)

                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("������").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4).withCheckable(false),
                        new PrimaryDrawerItem().withName("�������").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(4).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("����").withIcon(R.drawable.ava_dave).withIdentifier(1).withCheckable(false),
                        new PrimaryDrawerItem().withName("���� �������").withIcon(R.drawable.ava_dave_y).withIdentifier(2).withCheckable(false),
                        new PrimaryDrawerItem().withName("�����").withIcon(R.drawable.ava_ana).withIdentifier(3).withCheckable(false),
                        new DividerDrawerItem(),
//                        new PrimaryDrawerItem().withDescription("A more complex sample").withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(6).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_battery_charging_full).withIdentifier(7).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(8).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_my_location).withIdentifier(9).withCheckable(false),
//                        new SectionDrawerItem().withName(R.string.element),
                        new SecondaryDrawerItem().withName("���������").withIcon(FontAwesome.Icon.faw_github).withIdentifier(20).withCheckable(false),
                        new SecondaryDrawerItem().withName("������").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(10).withTag("Bullhorn")
//                        new DividerDrawerItem()
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                /*
                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleCompactHeaderDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, ActionBarDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, MultiDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleNonTranslucentDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, ComplexHeaderDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleFragmentDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, EmbeddedDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, FullscreenDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, CustomContainerActivity.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(SimpleHeaderDrawerActivity.this);
                                        */
                            }

                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 10
            result.setSelectionByIdentifier(10, false);

            //set the active profile
            headerResult.setActiveProfile(adultProfile);
        }
    }
    /*
    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };
    */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
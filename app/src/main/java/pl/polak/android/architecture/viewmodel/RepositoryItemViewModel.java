package pl.polak.android.architecture.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

import pl.polak.android.architecture.model.network.model.Repository;

public class RepositoryItemViewModel implements Parcelable {

    private long id;
    private String title;
    private String description;
    private int forks;
    private int watchers;
    private int stars;

    public RepositoryItemViewModel(Repository repository) {
        id = repository.getId();
        title = repository.getName();
        description = repository.getDescription();
        forks = repository.getForks();
        watchers = repository.getWatchers();
        stars = repository.getStars();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getForks() {
        return forks;
    }

    public int getWatchers() {
        return watchers;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.forks);
        dest.writeInt(this.watchers);
        dest.writeInt(this.stars);
    }

    protected RepositoryItemViewModel(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.forks = in.readInt();
        this.watchers = in.readInt();
        this.stars = in.readInt();
    }

    public static final Creator<RepositoryItemViewModel> CREATOR = new Creator<RepositoryItemViewModel>() {
        @Override
        public RepositoryItemViewModel createFromParcel(Parcel source) {
            return new RepositoryItemViewModel(source);
        }

        @Override
        public RepositoryItemViewModel[] newArray(int size) {
            return new RepositoryItemViewModel[size];
        }
    };
}

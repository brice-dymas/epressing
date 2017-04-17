(function() {
    'use strict';

    angular
        .module('epressingApp')
     //   .factory('MaCommande',MaCommande)
        .controller('HomeController', HomeController);
/**
    function MaCommande (){
        var commande = this;
        commande.lignes=[];
        commande.add = function(lg){
            commande.lignes.push({
                id: null,
                quantite:lg.quantite,
                etat:lg.etat,
                produit:{
                    id:lg.produit.id,
                    libelle:lg.produit.libelle,
                    photo:lg.produit.photo,
                    photoContentType:lg.produit.photoContentType, 
                    categorie:{ 
                        id:lg.produit.categorie.id,
                        libelle:lg.produit.categorie.libelle
                    }
                },
                operation:{
                    id:lg.operation.id,
                    libelle:lg.operation.libelle,
                    description:lg.operation.description
                },
                caracteristique: {
                    couleur: lg.caracteristique.couleur,
                    marque: lg.caracteristique.marque,
                    libelle: lg.caracteristique.libelle
                }    
            })
        };
        commande.remove = function(lg){
            for(i=0;i<commande.lignes.length;i++){
                if(commande.lignes[i]=lg){
                    commande.lignes[i].splice(lg,1);
                }
            }
        };
        return commande;
    };
     */
    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();

(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('CartController', CartController);

    CartController.$inject = ['$scope', '$stateParams', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande', 'Operation', 'Tarif'];

    function CartController ($scope, $stateParams,  DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, Operation, Tarif)  {
        var vm = this; 
        vm.order = entity; 
        console.log($scope.user);
        //vm.commande = $rootScope.maCommande;
        vm.ligneCommandes = $rootScope.commandeForm.ligneCommandes;
        console.log($rootScope.commandeForm.ligneCommandes.length+' lignes de Commande dans $rootScope.commandeForm');
        console.log($rootScope.commandeForm.ligneCommandes);
        vm.save = save;
        vm.remove = remove; 

              
        function remove(position){ 
            $scope.commandeForm.ligneCommandes.splice(position,1);
        }
        function save () {
            $rootScope.ligneCommandes.push(vm.ligneCommande);
            console.log('la taille de la liste de commande est maintenant: '+$rootScope.commandeForm.ligneCommandes.length);
        };        

        
 }
})();

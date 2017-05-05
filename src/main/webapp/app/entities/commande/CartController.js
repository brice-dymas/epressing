(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('CartController', CartController);

    //CartController.$inject = ['$scope', '$stateParams', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande', 'Operation', 'Tarif'];

    //function CartController ($scope, $stateParams,  DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, Operation, Tarif)  {
        CartController.$inject = ['$scope', '$stateParams', 'DataUtils', '$rootScope','entity'];
        function CartController ($scope, $stateParams,  DataUtils, $rootScope, entity)  {
        var vm = this; 
        vm.order = entity; 
        vm.ligneCommandes = $rootScope.commandeForm.ligneCommandes;
        vm.save = save;
        vm.remove = remove; 

              
        function remove(position){ 
            $scope.commandeForm.ligneCommandes.splice(position,1);
        }
        function save () {
            $rootScope.ligneCommandes.push(vm.ligneCommande);
        };        

        
 }
})();

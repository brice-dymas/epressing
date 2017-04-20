(function() {
    'use strict';

    angular 
        .module('epressingApp')
        .controller('CartController', CartController);

    CartController.$inject = ['$scope', '$stateParams', 'DataUtils', '$rootScope','entity', 'Produit','Caracteristique', 'LigneCommande', 'Operation', 'Tarif'];

    function CartController ($scope, $stateParams,  DataUtils, $rootScope, entity,Produit,Caracteristique, LigneCommande, Operation, Tarif)  {
        var vm = this; 
        vm.order = entity; 
        //vm.commande = $rootScope.maCommande;
        vm.lignesCommandes = $rootScope.commandeForm.lignesCommandes;
        console.log($rootScope.commandeForm.lignesCommandes.length+' lignes de Commande dans $rootScope.commandeForm');
        console.log(vm.lignesCommandes.length+' lignes de Commande dans vm.lignesCommandes ');
        vm.operations = Operation.query();        
        vm.save = save;
        vm.remove = remove;

              
        function remove(position){
            $scope.commandeForm.lignesCommandes.splice(position,1);
        }
        function save () {
            $rootScope.lignesCommandes.push(vm.ligneCommande);
            console.log('la taille de la liste de commande est maintenant: '+$rootScope.commandeForm.lignesCommandes.length);
        };        

        
 }
})();
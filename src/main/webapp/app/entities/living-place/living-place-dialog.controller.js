(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('LivingPlaceDialogController', LivingPlaceDialogController);

    LivingPlaceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LivingPlace'];

    function LivingPlaceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LivingPlace) {
        var vm = this;

        vm.livingPlace = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.livingPlace.id !== null) {
                LivingPlace.update(vm.livingPlace, onSaveSuccess, onSaveError);
            } else {
                LivingPlace.save(vm.livingPlace, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:livingPlaceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

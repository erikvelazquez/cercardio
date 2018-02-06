(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BackgroundDialogController', BackgroundDialogController);

    BackgroundDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Background'];

    function BackgroundDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Background) {
        var vm = this;

        vm.background = entity;
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
            if (vm.background.id !== null) {
                Background.update(vm.background, onSaveSuccess, onSaveError);
            } else {
                Background.save(vm.background, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:backgroundUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
